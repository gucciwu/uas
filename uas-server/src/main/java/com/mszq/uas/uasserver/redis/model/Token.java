package com.mszq.uas.uasserver.redis.model;

public class Token {
	private String sessionId;
	private long allocatedTime;
	private long expireTime;
	private String token;
	private long appId;
	public Token(){

	}

	public Token(String ltokenText) throws Exception{
		String[] splits = ltokenText.split(";");
		try{
			sessionId = splits[0];
			allocatedTime = Long.parseLong(splits[1]);
			expireTime = Long.parseLong(splits[2]);
			appId = Long.parseLong(splits[3]);
		}catch(Exception ex){
			throw new Exception("ltoken error formation.");
		}
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public long getAllocatedTime() {
		return allocatedTime;
	}
	public void setAllocatedTime(long allocatedTime) {
		this.allocatedTime = allocatedTime;
	}
	public long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(sessionId);
		sb.append(";");
		sb.append(allocatedTime);
		sb.append(";");
		sb.append(expireTime);
		sb.append(";");
		sb.append(appId);
		return sb.toString();
	}


	
}
