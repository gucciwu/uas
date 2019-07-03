package com.mszq.platform.app.permission.dto;

import java.util.List;
import java.util.Map;

import com.mszq.platform.base.MenuTree;

public class PermissionWebDTO {
	private List<MenuTree> menuTree;
	private Map<String,String>  buttonMap;
	
	public PermissionWebDTO(){}
	
	public PermissionWebDTO(List<MenuTree> menuTree,Map<String,String>  buttonMap){
		this.menuTree=menuTree;
		this.buttonMap=buttonMap;
	}
	
	public List<MenuTree> getMenuTree() {
		return menuTree;
	}
	public void setMenuTree(List<MenuTree> menuTree) {
		this.menuTree = menuTree;
	}
	public Map<String, String> getButtonMap() {
		return buttonMap;
	}
	public void setButtonMap(Map<String, String> buttonMap) {
		this.buttonMap = buttonMap;
	}
	
	
	

}
