package com.mszq.uas.uasserver.redis.storage;

import com.mszq.uas.uasserver.redis.model.Session;
import com.mszq.uas.uasserver.redis.model.Token;


public interface DAO {

	public void addSession(Session session) throws Exception;
	public Session findSession(String sessionId) throws Exception;
	public void deleteSession(String sessionId) throws Exception;
	public void addToken(Token token) throws Exception;
	public Token findToken(String token) throws Exception;
	public void addLocker(String id) throws Exception;
	public long findLocker(String id) throws Exception;
	public void updateErrorCount(String id, int times) throws Exception;
	public int findErrorCount(String id) throws Exception;
	public void deleteErrorCount(String id) throws Exception;
}
