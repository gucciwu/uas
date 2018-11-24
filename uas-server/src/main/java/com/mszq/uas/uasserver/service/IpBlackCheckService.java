package com.mszq.uas.uasserver.service;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.dao.mapper.IpListMapper;
import com.mszq.uas.uasserver.dao.model.IpList;
import com.mszq.uas.uasserver.exception.IpForbbidenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class IpBlackCheckService {

    @Autowired
    private IpListMapper ipListMapper;

    private String[] blackIps;

    //刷新IP列表指令
    private static final String REFRESH_IP_LIST_URL = "/config/refresh_ip_list";

    public void refreshBlackList(){
        List<IpList> list = ipListMapper.selectAll();
        blackIps = new String[list.size()];
        for(int i=0;i<list.size();i++) {
            blackIps[i] = list.get(i).getIp();
        }
    }

    public com.mszq.uas.uasserver.bean.Response isBlackList(String remoteIp) throws IpForbbidenException {
        if(blackIps == null){
            refreshBlackList();
        }

        if (remoteIp != null) {
            for (String ip : blackIps) {
                if (remoteIp.equals(ip)) {
                    throw new IpForbbidenException();
                }
            }
        }

        return null;
    }

    public com.mszq.uas.uasserver.bean.Response isBlackList(HttpServletRequest httpRequest) throws IpForbbidenException {
        String remoteIp = httpRequest.getRemoteAddr();
        return this.isBlackList(remoteIp);
    }
}
