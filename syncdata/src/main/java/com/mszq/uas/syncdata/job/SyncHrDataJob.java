package com.mszq.uas.syncdata.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.basement.Constant;
import com.mszq.uas.syncdata.mode.Org;
import com.mszq.uas.syncdata.mode.Role;
import com.mszq.uas.syncdata.mode.User;
import com.mszq.uas.syncdata.service.LastSyncRemarkService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

public class SyncHrDataJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SyncHrDataJob.class);
    @Autowired
    LastSyncRemarkService lastSyncRemarkService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${hr.api.url.start-session}")
    private String startSessionUrl;

    @Value("${hr.api.url.get-data}")
    private String getDataUrl;

    @Value("${hr.api.account}")
    private String apiAccount;

    @Value("${hr.api.password}")
    private String apiPassword;

    @Value("${uas.api.app-id}")
    private int uasAppId;
    @Value("${uas.api.secret}")
    private String uasSecret;
    @Value("${uas.api.url}")
    private String uasApiUrl;
    @Value("${sync.full-data}")
    private boolean isFullDate;
    @Value("${sync.employee-role-id}")
    private long employeeRoleId;
    @Value("${sync.employee-role-type-id}")
    private int employeeRoleTypeId;
    @Value("${sync.hr-org-type-id}")
    private int hrOrgTypeId;
    @Value("${sync.employee.status.ok}")
    private Set<Integer> employeeStatusOK;

    private JSONObject post(String url, JSONObject request){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<String>(request.toJSONString(),headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if(response.getStatusCodeValue() != 200){
            logger.error("Failure in invoking HR api:["+url+"]");
            return null;
        }

        return JSON.parseObject(response.getBody());
    }

    private int evalOrgGrade(Map<Integer,Org> map, Integer parentOrgId){
        int grade = 0;
        Org parentOrg = map.get(parentOrgId);
        if(parentOrg == null){
            return grade;
        }else{
            return evalOrgGrade(map,parentOrg.getAdminId())+1;
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Date lastSyncDate =new Date(lastSyncRemarkService.getLastSyncDate().getTime());

        String token = aquireToken();

        if(token != null) {
            syncOrgData(token, lastSyncDate);

            syncHrRoleData(token, lastSyncDate);

            syncHrEmployeeData(token, lastSyncDate);
        }

        try {
            lastSyncRemarkService.remark();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取HR系统API接口访问的Token
     * @return
     */
    public String aquireToken(){
        logger.trace("Aquire token");
        JSONObject tokenRequest = new JSONObject();
        tokenRequest.put("acc",apiAccount);
        tokenRequest.put("pwd",apiPassword);

        JSONObject tokenResponse =  post(startSessionUrl, tokenRequest);
        if(tokenResponse == null)
            return null;

        String token = tokenResponse.getString("Result");
        logger.trace("Token="+token);
        return token;
    }

    /**
     * 同步组织机构信息
     */
    public void syncOrgData(String token, Date lastDate){
        logger.trace("=====================Synchronize Organizations=====================");
        if (token != null && token.length() > 0) {
            JSONObject orgRequest = new JSONObject();
            orgRequest.put("funcId",6);
            JSONObject params = new JSONObject();
            params.put("depid",15);
            orgRequest.put("paras",params);
            orgRequest.put("dataFormat","json");
            orgRequest.put("dataPart","DS");
            orgRequest.put("accessToken",token);

            JSONObject orgResponse = post(getDataUrl, orgRequest);
            logger.trace(orgResponse.toJSONString());

            try {
                Map<Integer, Org> orgMap = new HashMap<Integer,Org>();
                if(orgResponse.getInteger("MsgId") == 0){
                    JSONArray orgRows = orgResponse.getJSONObject("Result").getJSONObject("oDep").getJSONArray("Row");
                    logger.trace("Get "+orgRows.size()+" orgs' data");
                    for(int i=0;i<orgRows.size();i++){
                        JSONObject dep = orgRows.getJSONObject(i);
                        logger.trace("Sync org:"+dep.toJSONString());
                        Org org = new Org();
                        org.setName(dep.getString("Title"));
                        if(dep.get("isDisabled") != null && dep.getInteger("isDisabled") == 1){
                            org.setStatus(Constant.ORG_STATUS.UNSIGNED);
                        }else{
                            org.setStatus(Constant.ORG_STATUS.OK);
                        }
                        org.setOrgType(Constant.ORG_TYPE.HR);
                        org.setOrgId(dep.getLong("DepID"));
                        org.setParentOrgId(dep.getLong("AdminID"));
                        org.setComment(dep.getString("DepCode"));
                        org.setHrId(dep.getInteger("DepID"));
                        if(dep.get("AdminID") != null) {
                            org.setAdminId(dep.getInteger("AdminID"));
                        }
                        org.setModifyTime(dep.getDate("lastupdatetime"));
                        orgMap.put(dep.getInteger("DepID"),org);
                    }

                    for(Org org:orgMap.values()){
                        if(!isFullDate && org.getModifyTime().before(lastDate))
                            continue;

                        org.setGrade(evalOrgGrade(orgMap,org.getAdminId()));
                        JSONObject req = new JSONObject();
                        req.put("_appId",uasAppId);
                        req.put("_secret",uasSecret);
                        req.put("org",org);

                        JSONObject updateOrgResponse = post(uasApiUrl + "/datasync/update_org", req);
                        if(updateOrgResponse.getInteger("code") != CODE.SUCCESS){
                            logger.error(updateOrgResponse.getString("msg"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void syncHrRoleData(String token, Date lastDate){
        logger.trace("=====================Synchronize role=====================");
        if (token != null && token.length() > 0) {
            JSONObject roleRequest = new JSONObject();
            roleRequest.put("funcId",7);
            JSONObject params = new JSONObject();
            roleRequest.put("paras",params);
            roleRequest.put("dataFormat","json");
            roleRequest.put("dataPart","DS");
            roleRequest.put("accessToken",token);

            JSONObject roleResponse = post(getDataUrl, roleRequest);
            logger.trace(roleResponse.toJSONString());

            try {
                if(roleResponse.getInteger("MsgId") == 0) {
                    JSONArray roleRows = roleResponse.getJSONObject("Result").getJSONObject("oJob").getJSONArray("Row");
                    logger.trace("Get "+roleRows.size()+" roles' data");
                    int count = 0;
                    for(int i=0;i<roleRows.size();i++){
                        JSONObject r = roleRows.getJSONObject(i);

                        if(!isFullDate && (r.getDate("lastupdatetime") == null || r.getDate("lastupdatetime").before(lastDate))) {
                            logger.trace("[skip]Sync org:"+r.toJSONString());
                            continue;
                        }
                        logger.trace("Sync org :"+r.toJSONString());
                        count++;

                        Role role = new Role();
                        role.setParentId(employeeRoleId);
                        role.setRoleName(r.getString("Title"));
                        role.setRoleTypeId(employeeRoleTypeId);
                        if(r.get("isDisabled") != null && r.getInteger("isDisabled") == 1){
                            role.setStatus(Constant.ROLE_STATUS.UNSIGNED);
                        }else{
                            role.setStatus(Constant.ROLE_STATUS.OK);
                        }

                        role.setRoleCode(r.getString("JobID"));

                        //查找角色是否存在
                        JSONObject findRoleReq = new JSONObject();
                        findRoleReq.put("_appId",uasAppId);
                        findRoleReq.put("_secret",uasSecret);
                        findRoleReq.put("roleCode",role.getRoleCode());

                        JSONObject findRoleResponse = post(uasApiUrl + "/permission/get_roles", findRoleReq);
                        if(findRoleResponse.getInteger("code") != CODE.SUCCESS){
                            logger.error(findRoleResponse.getString("msg"));
                        }

                        if(findRoleResponse.getJSONArray("data") == null || findRoleResponse.getJSONArray("data").size() == 0){
                            logger.info("Role is not existent. Create:"+r.toJSONString());
                            //没有找到，则创建
                            JSONObject addRoleReq = new JSONObject();
                            addRoleReq.put("_appId",uasAppId);
                            addRoleReq.put("_secret",uasSecret);
                            addRoleReq.put("role",role);
                            JSONObject addRoleResponse = post(uasApiUrl + "/permission/add_role", addRoleReq);
                            if(addRoleResponse.getInteger("code") != CODE.SUCCESS){
                                logger.error(addRoleResponse.getString("msg"));
                            }
                        }else{
                            //已经存在则更新
                            logger.info("Role is existent, Update:"+r.toJSONString());
                            long roleId = findRoleResponse.getJSONArray("data").getJSONObject(0).getLong("id");

                            JSONObject updateRoleReq = new JSONObject();
                            updateRoleReq.put("_appId",uasAppId);
                            updateRoleReq.put("_secret",uasSecret);
                            role.setId(roleId);
                            updateRoleReq.put("role",role);
                            JSONObject updateRoleResponse = post(uasApiUrl + "/permission/modify_role", updateRoleReq);
                            if(updateRoleResponse.getInteger("code") != CODE.SUCCESS){
                                logger.error(updateRoleResponse.getString("msg"));
                            }
                        }
                    }

                    logger.trace("Updated "+count+" roles' data totally.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void syncHrEmployeeData(String token, Date lastDate){
        logger.trace("=====================Synchronize Employee=====================");
        if (token != null && token.length() > 0) {
            JSONObject empRequest = new JSONObject();
            empRequest.put("funcId",9);
            JSONObject params = new JSONObject();
            empRequest.put("paras",params);
            empRequest.put("dataFormat","json");
            empRequest.put("dataPart","DS");
            empRequest.put("accessToken",token);

            JSONObject roleResponse = post(getDataUrl, empRequest);
            logger.trace(roleResponse.toJSONString());

            Map<String,List<JSONObject>> subJobs = syncHrSubJobData(token);

            try {
                if(roleResponse.getInteger("MsgId") == 0) {
                    JSONArray empRows = roleResponse.getJSONObject("Result").getJSONObject("Emp").getJSONArray("Row");
                    logger.trace("Get "+empRows.size()+" employees' data");
                    int count = 0;
                    for(int i=0;i<empRows.size();i++){
                        JSONObject u = empRows.getJSONObject(i);

                        if(!isFullDate && (u.getDate("lastupdatetime") == null || u.getDate("lastupdatetime").before(lastDate))) {
                            logger.trace("[skip]Sync user:"+u.toJSONString());
                            continue;
                        }
                        logger.trace("Sync user:"+u.toJSONString());
                        count++;

                        if("10006".equals(u.getString("Badge"))){
                            System.out.println("debug");
                        }

                        User user = new User();
                        Integer status = u.getInteger("JobStatus");
                        if(employeeStatusOK.contains(status)){
                            user.setStatus(Constant.USER_STATUS.OK);
                        }else{
                            user.setStatus(Constant.USER_STATUS.UNSIGNED);
                        }
                        user.setOrgId(u.getLong("DepID"));
                        user.setOrgType((short)hrOrgTypeId);
                        user.setJobNumber(u.getString("Badge"));
                        user.setName(u.getString("Name"));
                        user.setUserName(u.getString("Name"));
                        user.setIdType(u.getShort("CertType"));
                        user.setIdNumber(u.getString("CertNo"));
                        user.setMobile(u.getString("Mobile"));
                        user.setEmail(u.getString("Email"));
                        user.setTel(u.getString("office_phone"));
                        //更新用户信息
                        JSONObject updateUserReq = new JSONObject();
                        updateUserReq.put("_appId",uasAppId);
                        updateUserReq.put("_secret",uasSecret);
                        updateUserReq.put("user",user);
                        JSONObject updateUserResponse = post(uasApiUrl + "/datasync/update_user", updateUserReq);
                        if(updateUserResponse.getInteger("code") != CODE.SUCCESS){
                            logger.error(updateUserResponse.getString("msg"));
                        }
                        long userId = updateUserResponse.getLong("userId");

                        //添加员工角色（每个正式员工都必须添加）
                        JSONObject addEmployeeRoleReq = new JSONObject();
                        addEmployeeRoleReq.put("_appId",uasAppId);
                        addEmployeeRoleReq.put("_secret",uasSecret);
                        addEmployeeRoleReq.put("autoAddAccount", true);
                        addEmployeeRoleReq.put("roleId", employeeRoleId);
                        addEmployeeRoleReq.put("userId", userId);
                        JSONObject addEmployeeRoleResponse = post(uasApiUrl + "/permission/add_role_to_user", addEmployeeRoleReq);
                        if(addEmployeeRoleResponse.getInteger("code") != CODE.SUCCESS){
                            logger.error(addEmployeeRoleResponse.getString("msg"));
                        }

                        //添加主岗角色
                        //查找角色是否存在
                        JSONObject findMainRoleReq = new JSONObject();
                        findMainRoleReq.put("_appId",uasAppId);
                        findMainRoleReq.put("_secret",uasSecret);
                        findMainRoleReq.put("roleCode",u.getString("JobID"));

                        JSONObject findMainRoleResponse = post(uasApiUrl + "/permission/get_roles", findMainRoleReq);
                        if(findMainRoleResponse.getInteger("code") != CODE.SUCCESS){
                            logger.error(findMainRoleResponse.getString("msg"));
                        }

                        if(findMainRoleResponse.getJSONArray("data") != null && findMainRoleResponse.getJSONArray("data").size() != 0){
                            long mainRoleId = findMainRoleResponse.getJSONArray("data").getJSONObject(0).getLong("id");

                            JSONObject addMainRoleReq = new JSONObject();
                            addMainRoleReq.put("_appId",uasAppId);
                            addMainRoleReq.put("_secret",uasSecret);
                            addMainRoleReq.put("autoAddAccount", true);
                            addMainRoleReq.put("roleId", mainRoleId);
                            addMainRoleReq.put("userId", userId);

                            JSONObject addMainRoleResponse = post(uasApiUrl + "/permission/add_role_to_user", addMainRoleReq);
                            if(addMainRoleResponse.getInteger("code") != CODE.SUCCESS){
                                logger.error(addMainRoleResponse.getString("msg"));
                            }

                            List<JSONObject> list = subJobs.get(user.getJobNumber());
                            if(list != null){
                                for(JSONObject p:list){
                                    logger.trace("Sync subjob:"+p.toJSONString());
                                    //查找角色是否存在
                                    JSONObject findRoleReq = new JSONObject();
                                    findRoleReq.put("_appId",uasAppId);
                                    findRoleReq.put("_secret",uasSecret);
                                    findRoleReq.put("roleCode",p.getString("PartJobID"));

                                    JSONObject findRoleResponse = post(uasApiUrl + "/permission/get_roles", findRoleReq);
                                    if(findRoleResponse.getInteger("code") != CODE.SUCCESS){
                                        logger.error(findRoleResponse.getString("msg"));
                                    }

                                    if(findRoleResponse.getJSONArray("data") != null && findRoleResponse.getJSONArray("data").size() != 0) {
                                        long roleId = findRoleResponse.getJSONArray("data").getJSONObject(0).getLong("id");

                                        JSONObject addRoleReq = new JSONObject();
                                        addRoleReq.put("_appId", uasAppId);
                                        addRoleReq.put("_secret", uasSecret);
                                        addRoleReq.put("autoAddAccount", true);
                                        addRoleReq.put("roleId", roleId);
                                        addRoleReq.put("userId", userId);

                                        JSONObject addRoleResponse = post(uasApiUrl + "/permission/add_role_to_user", addRoleReq);
                                        if (addRoleResponse.getInteger("code") != CODE.SUCCESS) {
                                            logger.error(addRoleResponse.getString("msg"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    logger.trace("Updated "+count+" employees' data totally");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String,List<JSONObject>> syncHrSubJobData(String token){
        logger.trace("---------------------Synchronize SubJob---------------------");
        JSONObject subJobRequest = new JSONObject();
        subJobRequest.put("funcId",10);
        JSONObject params = new JSONObject();
        subJobRequest.put("paras",params);
        subJobRequest.put("dataFormat","json");
        subJobRequest.put("dataPart","DS");
        subJobRequest.put("accessToken",token);

        JSONObject roleResponse = post(getDataUrl, subJobRequest);
        logger.trace(roleResponse.toJSONString());

        Map<String,List<JSONObject>> map = new HashMap<String,List<JSONObject>>();
        try {
            if(roleResponse.getInteger("MsgId") == 0) {
                JSONArray ePartRows = roleResponse.getJSONObject("Result").getJSONObject("ePart").getJSONArray("Row");
                logger.trace("Get "+ePartRows.size()+" subjobs' data");
                for(int i=0;i<ePartRows.size();i++){
                    JSONObject p = ePartRows.getJSONObject(i);
                    if(map.get(p.getString("Badge")) == null){
                        map.put(p.getString("Badge"), new ArrayList<JSONObject>());
                    }

                    map.get(p.getString("Badge")).add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
