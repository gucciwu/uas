package com.mszq.platform.entity;

import java.io.Serializable;
import java.util.List;

import com.mszq.platform.app.dictionary.dto.DictionaryTreeDTO;

public class Dictionary implements Serializable {
	/**
     * 序列化
     */
    private static final long serialVersionUID = 2530444275345753329L;

    private int id;
	private String name;
	private String code;
	private int status;
	private int creatorId;
	private int parentId;
	private int isLeaf;
	private int hasItem;
	private String levelCode;
	
	private List<Dictionary> childDictionary;
	private List<DictionaryItem> childDicItem;
	
	public Dictionary(){//每个实体的都要写一个默认的构造函数
		
	}
	/**
	 * 创建主表使用
	 * @param name
	 * @param code
	 * @param parentId
	 */
	public Dictionary(String name,String code,int parentId){
		this.parentId=parentId;
		this.name=name;
		this.code=code;
		this.parentId=parentId;
	}
	public Dictionary(int id,String name,int status){
		this.id=id;
		this.name=name;
		this.status=status;
	}
	public Dictionary(int id,int parentId,String name,String code,int status,int isLeaf,int hasItem,String levelCode){
		this.id=id;
		this.parentId=parentId;
		this.name=name;
		this.code=code;
		this.status=status;
		this.isLeaf=isLeaf;
		this.hasItem=hasItem;
		this.levelCode=levelCode;
	}
	public Dictionary(int id,int parentId,String name,String code,int status,int creatorId,int isLeaf,int hasItem,String levelCode){
		this.id=id;
		this.parentId=parentId;
		this.name=name;
		this.code=code;
		this.status=status;
		this.creatorId=creatorId;
		this.isLeaf=isLeaf;
		this.hasItem=hasItem;
		this.levelCode=levelCode;
	}
	
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}
	public int getHasItem() {
		return hasItem;
	}
	public void setHasItem(int hasItem) {
		this.hasItem = hasItem;
	}
	public List<Dictionary> getChildDictionary() {
		return childDictionary;
	}
	public void setChildDictionary(List<Dictionary> childDictionary) {
		this.childDictionary = childDictionary;
	}
	public List<DictionaryItem> getChildDicItem() {
		return childDicItem;
	}
	public void setChildDicItem(List<DictionaryItem> childDicItem) {
		this.childDicItem = childDicItem;
	}
	
	
	

}
