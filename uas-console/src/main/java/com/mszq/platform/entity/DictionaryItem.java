package com.mszq.platform.entity;

import java.io.Serializable;

public class DictionaryItem implements Serializable {
	/**
     * 序列化
     */
    private static final long serialVersionUID = 6466902088243633107L;

    private int id;
	private int dicId;//主表ID
	private String name;
	private String value;
	private int status;
	private int creatorId;
	 
	public DictionaryItem(){}//默认构造函数必须要，mybatis需要
	 
	 public DictionaryItem(int id,String name,String value,int status){
		 this.id=id;
		 this.name=name;
		 this.value=value;
		 this.status=status;
	 }
	 public DictionaryItem(int id,int dicId,String name,String value,int status,int creatorId){
		 this.id=id;
		 this.dicId=dicId;
		 this.name=name;
		 this.value=value;
		 this.status=status;
		 this.creatorId=creatorId;
	 }
	public int getCreatorId() {
		return creatorId;
	}
	public int getDicId() {
		return dicId;
	}

	public void setDicId(int dicId) {
		this.dicId = dicId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	

}
