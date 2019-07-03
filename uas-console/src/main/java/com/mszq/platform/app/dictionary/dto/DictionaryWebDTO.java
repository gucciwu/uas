package com.mszq.platform.app.dictionary.dto;

/**
 * conroller向前台传递的json对象
 * @author michel
 * 向前端返回的类的属性必须有get/set方法，这样springmvc才可设置返回的属性的值
 *
 */
public class DictionaryWebDTO {
	private int id;//
	private String name;
	private String text;//值等于name，纯粹为了combotree使用[id,text,children]
	private String value;
	private int status;
	private String state;//这是给前端easyui的treegird用的属性，代表有没有子节点 closed为有子节点，open为没有子节点
	private int  parentId;//这是给前端easyui的treegird用的属性
	private int type;//0是主表，1是子表，
	private String levelCode;
	private String code;//代码，主表数据有
	
    public DictionaryWebDTO(){//默认构造方法，springmvc将前台的数据绑定到DictionaryWebDTO对象时需要
		
	}
    
    /**
     * 供前端创建数据字典使用
     * @param id
     * @param name
     * @param code
     * @param text
     * @param value
     * @param status
     * @param state
     * @param parentId
     * @param type
     */
    public DictionaryWebDTO(int id,String name,String code,String text,String value,int status,String state,int parentId,int type){
		this.id=id;
		this.name=name;
		this.code=code;
		this.text=text;
		this.status=status;
		this.state=state;
		this.parentId= parentId;
		this.type=type;
	}	
	/**
	 * 供后端向前端传递数据用
	 * @param id
	 * @param name
	 * @param value
	 * @param status
	 * @param state
	 * @param parentId
	 * @param type
	 * @param level_code
	 */
	public DictionaryWebDTO(int id,String name,String value,int status,String state,int parentId,int type,String level_code){
		this.id=id;
		this.name=name;
		this.text=name;
		this.value=value;
		this.status=status;
		this.state=state;
		this.parentId= parentId;
		this.type=type;
		this.levelCode=level_code;
	}	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
	

}
