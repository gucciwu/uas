package com.mszq.uas.sso.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mszq.uas.basement.CODE;
import com.mszq.uas.sso.Constants;
import com.mszq.uas.sso.service.PasswordCheck;
import com.mszq.uas.sso.service.UasService;
import com.mszq.uas.uasserver.bean.AuthResponse;
import com.mszq.uas.uasserver.bean.VerifyTokenResponse;
import com.mszq.uas.uasserver.dao.model.App;
import com.mszq.uas.uasserver.dao.model.Org;
import com.mszq.uas.uasserver.dao.model.User;
import org.jcp.xml.dsig.internal.dom.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class Controller {

    /** 统一登陆界面 */
    static final String LOGIN_PATH = "index.html";
    /** 用户认证成功后获取的session id*/
    public static final String PARAM_SESSIONID="sessionid";
    /** targetappid为该需要登录的目标系统appid */
    public static final String PARAM_TARGET_APPID="targetappid";
    /** appid为该应用系统在统一认证中分配的应用编码 */
    public static final String PARAM_APPID="appid";
    /** service为认证成功后重定向的地址，需要URLENCODE */
    public static final String PARAM_SERVICE="service";

    public static final Random random=new Random();
    public static final Font[] dFont=new Font[] {
            new Font("nyala", Font.BOLD, 24), new Font("Bell MT", Font.PLAIN, 24),
            new Font("Credit valley", Font.BOLD, 24) };

    @Autowired
    private UasService uasService;

    @RequestMapping("/ApplyToken")
    protected void applyToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetappid=request.getParameter(PARAM_TARGET_APPID);
        String appid=request.getParameter(PARAM_APPID);
        String sessionId=request.getParameter(PARAM_SESSIONID);

        HttpSession session=request.getSession();
        if(session != null){
            Object obj=session.getAttribute(Constants.SESSION_SESSIONID);
            if(obj != null){
                sessionId = obj.toString();
            }
        }
        try {
            if(appid == null || targetappid == null || sessionId == null){
                System.out.println("appid,targetappid,sessionid不能为空");
                response.setContentType("application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();

                JSONObject ob = new JSONObject();
                ob.put("code", "-1");
                ob.put("message", "appid,targetappid,sessionid不能为空");
                out.print(ob.toString());
                out.flush();
            }else{
                String ticket=uasService.getToken(targetappid,sessionId);
                System.out.println("TICKET:"+ticket);
                if(ticket==null||"".equals(ticket)){
                    response.setContentType("application/json;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    JSONObject ob = new JSONObject();
                    ob.put("code", "-2");
                    ob.put("message", "申请子令牌失败，禁止单点登录目标系统");
                    out.print(ob.toString());
                    out.flush();
                }else{
                    response.setContentType("application/json;charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    JSONObject ob = new JSONObject();
                    ob.put("code", "1");
                    ob.put("message", "申请令牌成功");
                    ob.put("token", ticket);
                    out.print(ob.toString());
                    out.flush();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/jcaptcha")
    protected void imageCaptcha(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse) throws ServletException,
            IOException {
        genernateCaptchaImage(httpServletRequest, httpServletResponse);
    }

    private Color getRandomColor(){
        return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
    }
    private Font getRandomFont(){
        return dFont[random.nextInt(dFont.length)];
    }
    private String getRandomNumber(int bit){
        StringBuilder sb=new StringBuilder();
        String temp="1234567890";
        int max=10;
        for(int i=0;i<bit;i++){
            int t=random.nextInt(max);
            sb.append(temp.charAt(t));
        }
        return sb.toString();
    }
    /**
     * 生成验证码图片.
     */
    private void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        int width=100,height=32;
        ServletOutputStream out = response.getOutputStream();
        try {
            String captchaId = request.getSession(true).getId();
            BufferedImage image = new BufferedImage(100, 32, BufferedImage.TYPE_INT_BGR);
            //获取图片处理对象
            Graphics graphics = image.getGraphics();
            //填充背景色
            graphics.setColor(new Color(255,255,255));
            graphics.fillRect(0, 0, width, height);
            //设置干扰线
            graphics.setColor(getRandomColor());
            Random random = new Random();
            for(int i=0;i<10;i++){
                int x = random.nextInt(width - 1);
                int y = random.nextInt(height - 1);
                int x1 = random.nextInt(width - 1);
                int y1 = random.nextInt(height - 1);
                graphics.drawLine(x, y, x1, y1);
            }
            //写入文字
            graphics.setColor(getRandomColor());
            graphics.setFont(getRandomFont());
            String content = new String(getRandomNumber(4));
            request.getSession().setAttribute(Constants.SESSION_YZM, content);
            graphics.drawString(content, 16, 24);

            //释放资源
            graphics.dispose();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (Exception e) {
        } finally {
            out.close();
        }
    }

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, NumberFormatException,IOException {
        // TODO Auto-generated method stub
        String service=request.getParameter(PARAM_SERVICE);
        String appid=request.getParameter(PARAM_APPID);
        String sessionId=null;
        HttpSession session=request.getSession();
        Object obj=session.getAttribute(Constants.SESSION_SESSIONID);
        System.out.println(PARAM_SERVICE+":"+service);
        System.out.println(PARAM_APPID+":"+appid);
        if(service==null||appid==null||service.indexOf("auims_ticket")!=-1){
            response.sendRedirect(LOGIN_PATH);
            return;
        }

        App app = uasService.getApp(Long.parseLong(appid));

        if(service.toLowerCase().indexOf(app.getPath().toLowerCase()) !=0 ){
            request.setAttribute("msg", "操作错误，不允许单点登录URL:"+service);
            request.getRequestDispatcher("main.jsp")
                    .forward(request, response);
            return;
        }

        session.setAttribute(Constants.SESSION_APPID, appid);
        session.setAttribute(Constants.SESSION_SERVICE, service);

        request.setAttribute(Constants.SESSION_APPID, appid);
        request.setAttribute(Constants.SESSION_SERVICE, service);

        if(obj!=null)sessionId=obj.toString();
        System.out.println("3:"+sessionId);
        if(sessionId!=null){//已经登陆过
            StringBuilder url=new StringBuilder();
            String ticket="";
            ticket= uasService.getToken(appid,sessionId);//申请子令牌
            System.out.println("TICKET:"+ticket);
            if(ticket==null||"".equals(ticket)){
                request.setAttribute("msg", "申请子令牌失败，禁止单点登录目标系统");
                request.getRequestDispatcher("main.jsp")
                        .forward(request, response);
            }else{
                url.append(service).append(service.indexOf("?")==-1?"?":"&").append("auims_ticket=").append(ticket);
                response.sendRedirect(url.toString());
            }
            return;
        }else{//未登陆
            //request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
            if(request!=null&&request.getRequestDispatcher("index.html")!=null){
                String temp=request.getParameter("msg");
                if(temp!=null)request.setAttribute("msg", temp);
                request.getRequestDispatcher("index.html")
                        .forward(request, response);
            }else
                response.sendRedirect(LOGIN_PATH);
        }
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sessionid=request.getParameter("sessionid");
        String service=request.getParameter(PARAM_SERVICE);
        String appid=request.getParameter(PARAM_APPID);

        if(sessionid==null&&request.getSession().getAttribute(Constants.SESSION_SESSIONID)!=null){
            sessionid=request.getSession().getAttribute(Constants.SESSION_SESSIONID).toString();
        }

        if(sessionid!=null){
            uasService.logout(sessionid);
        }
        request.getSession().invalidate();
        if(service!=null){
            service=java.net.URLDecoder.decode(service);
            request.getSession().setAttribute(Constants.SESSION_SERVICE, service);
        }
        if(appid!=null){
            request.getSession().setAttribute(Constants.SESSION_APPID,appid);
        }
        request.setAttribute(Constants.SESSION_SERVICE, service);
        request.setAttribute(Constants.SESSION_APPID, appid);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @RequestMapping("/modify")
    protected void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userid=(String)session.getAttribute(Constants.SESSION_USERID);
        String appid = (String)session.getAttribute(Constants.SESSION_APPID);
        String oldpass = request.getParameter("opass");
        String newpass = request.getParameter("pass");
        String confirmpass = request.getParameter("cpass");
        //System.out.println(userid+" "+newpass+" "+confirmpass);
        Map<String,Object> json=new HashMap<String,Object>();
        if(PasswordCheck.check(newpass)){
            json.put("code", "-1");
            json.put("msg", "新密码不符合设置规范");
            this.gotoJsonPage(json, request, response);
        }
		if(oldpass==null||newpass==null||confirmpass==null||"".equals(oldpass)||"".equals(newpass)||"".equals(confirmpass)){
			request.setAttribute("msg","旧密码、新密码或确认密码不可为空！");
			request.getRequestDispatcher("modifypassword.jsp")
					.forward(request, response);
		}

		if(!newpass.equals(confirmpass)){
			request.setAttribute("msg","新密码和确认密码不一致！");
			request.getRequestDispatcher("modifypassword.jsp")
					.forward(request, response);
		}

        if(userid == null){
            if(request!=null&&request.getRequestDispatcher("index.jsp")!=null){
                String temp=request.getParameter("msg");
                if(temp!=null)request.setAttribute("msg", temp);
                request.getRequestDispatcher("index.jsp")
                        .forward(request, response);
            }else
                response.sendRedirect(LOGIN_PATH);
        }else{
            try {
                uasService.modifyPassword(userid, oldpass, newpass);
                json.put("code", 0);
                json.put("msg", "修改成功");
                this.gotoJsonPage(json, request, response);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }

            json.put("code", "-1");
            json.put("msg", "未知异常");
            this.gotoJsonPage(json, request, response);
        }
    }

    public void gotoJsonPage(Map<String,Object>json,HttpServletRequest request, HttpServletResponse response){
        try {
            request.setAttribute("JsonData", JSON.toJSONString(json));
            request.getRequestDispatcher("views/sys/json.jsp").forward(//ROOT_PATH +
                    request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/UserCheck")
    public void userCheck(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = request.getParameter("u");
        String pass = request.getParameter("p");
        String yzm=request.getParameter("yzm");

        String service="",targetAppid="";
        if(user!=null){
            user=user.replaceAll("[%]|[<]|[>]|[']||[\"]|[=]|[(]|[)]|[|]", "");
        }
        service=request.getParameter(Constants.SESSION_SERVICE);
        targetAppid=request.getParameter(Constants.SESSION_APPID);
        service=service==null?"":service.replaceAll("[\r]|[\n]|[<]|[>]|[(]|[)]|[']||[\"]", "");
        targetAppid=targetAppid==null?"":targetAppid.replaceAll("[\r]|[\n]|[<]|[>]|[(]|[)]|[']||[\"]", "");
        request.setAttribute(Constants.SESSION_SERVICE, service);
        request.setAttribute(Constants.SESSION_APPID, targetAppid);
        request.setAttribute("userid", user);

        if(yzm==null||request.getSession().getAttribute(Constants.SESSION_YZM)==null){
            request.setAttribute("msg","请输入验证码！");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
            return;
        }
        if(user==null||pass==null||"".equals(user)||"".equals(pass)){
            request.setAttribute("msg","用户名或密码不可为空！");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
            return;
        }
        String sessionYZM=request.getSession().getAttribute(Constants.SESSION_YZM).toString();
        if(!yzm.equals(sessionYZM)){
            request.setAttribute("msg","验证码有误！");
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
            return;
        }

        App app = uasService.getApp(Long.parseLong(targetAppid));
        if(service.toLowerCase().indexOf(app.getPath().toLowerCase()) !=0 ){
            request.setAttribute("msg", "操作错误，不允许单点登录URL:"+service);
            request.getRequestDispatcher("main.jsp")
                    .forward(request, response);
            return;
        }

//		//判断该用户的统一认证账户和OA账户是否已经同步密码？（1）账户新建，则直接提示用户首次登录，密码需要重置。
//		//（2）用户已存在单未同步密码，则提示用户统一认证账户需要与OA密码保持一致，需要重置密码。
//		//TODO 强制设置为需要重置密码，便于测试
//		FixSyncUserStatus util = new FixSyncUserStatus();
//		switch(util.getUserStatus(user)){
//			case 1:
//				request.setAttribute("status", 1);
//				request.getRequestDispatcher("resetoa.jsp").forward(request, response);
//				return;
//			case 2:
//				request.setAttribute("status", 2);
//				request.getRequestDispatcher("resetoa.jsp").forward(request, response);
//				return;
//			default:
//				break;
//		}

        AuthResponse resp = uasService.auth(user,pass);
        if (resp.getCode() != CODE.SUCCESS){
            request.setAttribute("msg", resp.getMsg());
            request.getRequestDispatcher("index.jsp")
                    .forward(request, response);
            return;
        } else {

            setSessionUser(user,request.getSession());
            String sessionId=resp.getSessionId();
            session.setAttribute(Constants.SESSION_USERID, resp.getUserId());
            session.setAttribute(Constants.SESSION_JOB_NUMBER, resp.getJobNumber());
            session.setAttribute(Constants.SESSION_SESSIONID, sessionId);

            if(PasswordCheck.check(pass)){
                request.getSession().setAttribute("MUST_EDIT_PWD", "true");
                request.setAttribute("msg", "您的登录密码强度不够，请立即修改密码！");
                request.getRequestDispatcher("modifypassword.jsp")
                        .forward(request, response);
                return;
            }

            if(service==null||"".equals(service)){
                //进入主页面
                response.sendRedirect("main");
            }else{
                //session.removeAttribute(Constants.SESSION_SERVICE);
                //session.removeAttribute(Constants.SESSION_APPID);
                StringBuilder url=new StringBuilder();
                String ticket="";
                ticket=uasService.getToken(targetAppid,sessionId);//申请子令牌
                if(ticket==null||"".equals(ticket)){
                    request.setAttribute("msg","申请子令牌失败，禁止单点登录目标系统");
                    request.getRequestDispatcher("main.jsp")
                            .forward(request, response);
                    return;
                }
                url.append(service).append(service.indexOf("?")==-1?"?":"&").append("auims_ticket=").append(ticket);
                response.sendRedirect(url.toString());
            }
        }
    }

    private void setSessionUser(String jobNumber,HttpSession session){
        User user = uasService.getUser(jobNumber);
        if(user != null) {
            //证件号需要处理一下，把中间6位变为*
            String zjh = user.getJobNumber();
            if (zjh != null && zjh.length() >= 15) {
                String nZjh = zjh.substring(0, (zjh.length() - 10)) + "******" + zjh.substring(zjh.length() - 4);
                zjh = nZjh;
            }
            user.setIdNumber(zjh);
            //手机号需要处理一下，把中间4为变为*
            String mobile = user.getMobile();
            if (mobile != null && mobile.length() >= 11) {
                String nMobile = mobile.substring(0, mobile.length() - 6) + "****" + mobile.substring(mobile.length() - 2);
                mobile = nMobile;
            }
            user.setMobile(mobile);

            Org org = uasService.getOrg(user.getOrgId(),user.getOrgType());
            session.setAttribute(Constants.SESSION_USER, user);
            session.setAttribute(Constants.SESSION_ORG,org);
        }

    }

    @RequestMapping("/validate")
    public void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token=request.getParameter("token");

        VerifyTokenResponse resp = uasService.validate(token);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(resp));
    }
}
