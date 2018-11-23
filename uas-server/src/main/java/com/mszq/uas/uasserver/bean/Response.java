package com.mszq.uas.uasserver.bean;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class Response<T> {
	@ApiModelProperty(value = "结果代码", dataType="Integer", notes="0表示成功，非0表示失败")
	private int code;
	@ApiModelProperty(value = "结果信息", dataType="String", notes="当code返回0时，该字段返回“处理成功”，否则返回错误原因")
	private String msg;
	@ApiModelProperty(value = "应答报文内容", dataType="JSONArray")
	private List<T> data;
	
	public Response(){}
	
	public Response(int code, String msg, List<T> data){
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public Response(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
