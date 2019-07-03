package com.mszq.platform.app.dictionary.dto;

import java.util.List;
import java.util.Set;

import com.mszq.platform.entity.Dictionary;
import com.mszq.platform.entity.DictionaryItem;

public class DictionaryTreeDTO {
	private int id;
	private int parentId;
	private String name;
	private String code;
	private boolean status;
	private long creatorId;
	private boolean isLeaf;//是否为叶子节点
	private boolean hasItem;//在子表中是否有数据
	private String levelCode;
	private List<DictionaryTreeDTO> childDictionaryTreeDTO;
	private List<DictionaryItem> childDicItem;
	public int getId() {
		return id;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isHasItem() {
		return hasItem;
	}
	public void setHasItem(boolean hasItem) {
		this.hasItem = hasItem;
	}
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	
	public List<DictionaryTreeDTO> getChildDictionaryTreeDTO() {
		return childDictionaryTreeDTO;
	}

	public void setChildDictionaryTreeDTO(List<DictionaryTreeDTO> childDictionaryTreeDTO) {
		this.childDictionaryTreeDTO = childDictionaryTreeDTO;
	}

	public List<DictionaryItem> getChildDicItem() {
		return childDicItem;
	}
	public void setChildDicItem(List<DictionaryItem> childDicItem) {
		this.childDicItem = childDicItem;
	}

	
	
	

}
