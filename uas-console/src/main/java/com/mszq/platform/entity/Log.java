package com.mszq.platform.entity;

import java.util.Date;

public class Log {
	private int id;
	private String account;
	private String name;
	private String message;
	private Date date;
	
	public Log(){
		
	}
	public Log(int id,String account,String name,String message,Date date){
		this.id=id;
		this.account=account;
		this.name=name;
		this.message=message;
		this.date=date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	

}
