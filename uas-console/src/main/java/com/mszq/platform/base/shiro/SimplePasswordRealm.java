package com.mszq.platform.base.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.mszq.platform.app.employee.service.IEmployeeService;
import com.mszq.platform.app.permission.service.IPermissionService;
import com.mszq.platform.app.role.service.IRoleService;
import com.mszq.platform.base.IConstants;
import com.mszq.platform.entity.Employee;
import com.mszq.platform.entity.Permission;


public class SimplePasswordRealm extends AuthorizingRealm{

	private final Logger logger=LoggerFactory.getLogger(SimplePasswordRealm.class);
//	private Employee employee=null;
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IPermissionService permissionService;
	 
	
	/*
	 * 认证：who are you?你是谁？
	 * 由shiro自动调用
	 * 在这里验证账号及密码
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken shiroToken) throws AuthenticationException {
		SimpleAuthenticationInfo satinfo=null;//认证信息
		SimpleAuthorizationInfo sazinfo=null;//授权信息
		String account="";//员工账号
		
		UsernamePasswordToken token=(UsernamePasswordToken)shiroToken;
		token.setRememberMe(false);
		account=token.getUsername();
		//TODO 根据code及password查询数据库中是否有次记录，如果有，则创建一个SimpleAuthenticationInfo对象并返回
		if(null!=account&&!"".equalsIgnoreCase(account)){
			Employee employee=employeeService.getEmployeeByAccount(account);
			if(null!=employee){
				// 账号未启用
		        if (employee.getStatus().intValue() == 0) {
		            return null;
		        }
		        String roleCodes=employeeService.getRolesByEmployeeID(Long.toString(employee.getId()));
		        employee.setRoleIds(roleCodes);
		        satinfo=new SimpleAuthenticationInfo(employee,employee.getPassword(),getName());//这个时候，shiro应该会比对SimpleAuthenticationInfo对象与token中存储的密码是否一致，如果不一致，则向login抛出异常，直接跳转至loginController
				//验证不通过时，shiro会抛出异常，直接跳转至LoginAction
				if(null!=satinfo){
					UserSecurityManager.setAttribute("ID", employee.getId());
					UserSecurityManager.setAttribute("account", employee.getAccount());
					UserSecurityManager.setAttribute("name", employee.getName());
					UserSecurityManager.setAttribute("orgnizationId", employee.getOrgnizationId());
					UserSecurityManager.setAttribute("roles", roleCodes);
					//如果是综合服务管理员、外包管理员、系统管理员
					if(StringUtils.hasText(roleCodes) && (roleCodes.contains(IConstants.ROLE_SYS_ADMIN)||roleCodes.contains(IConstants.ROLE_COMP_SERVICE_ADMIN)||roleCodes.contains(IConstants.ROLE_PP_SERVICE_ADMIN))){
						UserSecurityManager.setAttribute("isAdmin", true);
					}else {
						UserSecurityManager.setAttribute("isAdmin", false);
					}
					
				}
				
				
				
			}
		}
		return satinfo;
	}
	
	/*授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.通过这个方法获取角色、权限信息
	 * SecurityUtils.getSubject().isPermitted 或者@RequiresPermissions 会执行
	 * 授权：what can you do?你能干啥？
	 * 在login之后被自动调用
	 * 这里要获取用户的角色及权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo sazinfo=null;
//		String account="";
		Set<String> roleNameSet=new HashSet<String>();			
		Set<String> functionCodeSet=new HashSet<String>();//权限分为功能权限(包含菜单权限与动作权限)与数据权限
		Employee employee= (Employee)principals.getPrimaryPrincipal();
//		account=(String)principals.fromRealm(getName()).iterator().next();//
		sazinfo=new SimpleAuthorizationInfo();//授权信息
		//TODO 查询角色及权限（菜单、按钮等权限）
		if(!"".equalsIgnoreCase(employee.getAccount())){
			//TODO 获取当前账号的权限，修改中。。。。.
			List<Permission> permissions = permissionService.queryPermissionsByUserID((Long)UserSecurityManager.getAttribute("ID"));
			if(permissions != null){
				for (Permission permission : permissions) {
					functionCodeSet.add(permission.getUrl());
				}
			}
			if(StringUtils.hasText(employee.getRoleIds())){
				String[] roleCodes = employee.getRoleIds().split(",");
				if(roleCodes != null){
					for (String string : roleCodes) {
						if(StringUtils.hasText(string)){
							functionCodeSet.add(string);
						}
					}
				}
			}
			
			//设置role和permission后，，前台代码就可以使用标签来控制页面元素显示与否
			sazinfo.setRoles(roleNameSet);
			sazinfo.setStringPermissions(functionCodeSet);//前台控制权限时，仅需要功能权限
		}
		return sazinfo;
	}

	public void removeUserCache(){
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
//		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
//		SimplePasswordRealm simplePasswordRealm = (SimplePasswordRealm)rsm.getRealms().iterator().next();
//		simplePasswordRealm.removeUserCache();
    }
	

}
