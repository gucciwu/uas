package com.mszq.platform.app.agenda.dto;

import java.util.Date;

public class AgendaDTO {
   private Long id;
   private Integer creatorId;
   private String creatorName;
   private Integer productId;
   private String productFullName;
   private String title;
   private String start;
   private String end;
   private Byte isRemind;
   private String type;
   private Integer advanceTime;
   private Integer timeType;
   private String status;//显示的颜色
   public AgendaDTO(){}
   public AgendaDTO(Long id,Integer creatorId,String creatorName,Integer productId, String productFullName,String title,String start,String end,Byte isRemind,String type,Integer advanceTime,Integer timeType,String status){
	   this.id=id;
	   this.creatorId=creatorId;
	   this.creatorName=creatorName;
	   this.productId=productId;
	   this.productFullName=productFullName;
	   this.title=title;
	   this.start=start;
	   this.end=end;
	   this.isRemind=isRemind;
	   this.type=type;
	   this.advanceTime=advanceTime;
	   this.timeType=timeType;
	   this.status=status;//显示的颜色
   }
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getStart() {
	return start;
}
public void setStart(String start) {
	this.start = start;
}
public String getEnd() {
	return end;
}
public void setEnd(String end) {
	this.end = end;
}
public Byte getIsRemind() {
	return isRemind;
}
public void setIsRemind(Byte isRemind) {
	this.isRemind = isRemind;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public Integer getAdvanceTime() {
	return advanceTime;
}
public void setAdvanceTime(Integer advanceTime) {
	this.advanceTime = advanceTime;
}
public Integer getTimeType() {
	return timeType;
}
public void setTimeType(Integer timeType) {
	this.timeType = timeType;
}
public Integer getProductId() {
	return productId;
}
public void setProductId(Integer productId) {
	this.productId = productId;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getProductFullName() {
	return productFullName;
}
public void setProductFullName(String productFullName) {
	this.productFullName = productFullName;
}
public Integer getCreatorId() {
	return creatorId;
}
public void setCreatorId(Integer creatorId) {
	this.creatorId = creatorId;
}
public String getCreatorName() {
	return creatorName;
}
public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

}
