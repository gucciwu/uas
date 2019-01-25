package com.mszq.uas.uasserver.redis.storage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mszq.uas.uasserver.Config;
import com.mszq.uas.uasserver.redis.model.Session;
import com.mszq.uas.uasserver.redis.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 使用H2 Database实现数据存储访问
 *
 * @author liugy
 *
 */
//@Component
@Service
public class RedisDao implements DAO {

	private static Logger logger = LoggerFactory.getLogger(RedisDao.class);

	private static ObjectMapper mapper = new ObjectMapper();

	private final String TABLE_SESSION = "SESSION";
	private final String TABLE_TOKEN = "TOKEN";
	private final String LOCKER = "LOCKER";
	private final String ERROR = "ERROR";

	@Autowired
	@Qualifier("config")
	private Config config;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void addSession(Session session) throws IOException{
		long s = System.currentTimeMillis();

		try{
			redisTemplate.opsForValue().set(TABLE_SESSION+"_"+session.getSessionId(),
					mapper.writeValueAsString(session),
					config.getSessionTimeout(),
					TimeUnit.MILLISECONDS);
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[ADD SESSION]:"+(e-s)+"ms");
		}
	}

	@Override
	public Session findSession(String sessionId) throws Exception{
		long s = System.currentTimeMillis();

		try{
			String value = redisTemplate.opsForValue().get(TABLE_SESSION+"_"+sessionId);
			if(value == null){
				return null;
			}else{
				Session session = mapper.readValue(value, Session.class);
				return session;
			}
		} catch (IOException e) {
			return null;
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[FIND SESSION]:"+(e-s)+"ms");
		}

	}

	@Override
	public void deleteSession(String sessionId) {
		long s = System.currentTimeMillis();

		try{
			redisTemplate.delete(TABLE_SESSION+"_"+sessionId);
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[DELETE SESSION]:"+(e-s)+"ms");
		}
	}

	@Override
	public void addToken(Token token) throws Exception {
		long s = System.currentTimeMillis();

		try{
			redisTemplate.opsForValue().set(TABLE_TOKEN+"_"+token.getToken(),
					mapper.writeValueAsString(token),
					config.getTokenTimeout(),
					TimeUnit.MILLISECONDS);
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[ADD TOKEN]:"+(e-s)+"ms");
		}
	}

	@Override
	public Token findToken(String token) throws Exception {
		long s = System.currentTimeMillis();

		try{
			String value = redisTemplate.opsForValue().get(TABLE_TOKEN+"_"+token);
			if(value == null){
				return null;
			}else{
				Token t = mapper.readValue(value, Token.class);
				return t;
			}
		} catch (IOException e) {
			return null;
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[FIND TOKEN]:"+(e-s)+"ms");
		}
	}

	@Override
	public void addLocker(String id) throws Exception {
		long s = System.currentTimeMillis();

		try{
			redisTemplate.opsForValue().set(LOCKER+"_"+id,
					mapper.writeValueAsString(s),
					config.getLockTime(),
					TimeUnit.MILLISECONDS);
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[ADD LOCKER]:"+(e-s)+"ms");
		}
	}

	@Override
	public long findLocker(String id) throws Exception {
		long s = System.currentTimeMillis();

		try{
			String value = redisTemplate.opsForValue().get(LOCKER+"_"+id);
			if(value == null){
				return 0;
			}else{
				long t = Long.parseLong(value);
				long left = t+config.getLockTime()-s;
				return left;
			}
		} catch (NumberFormatException e) {
			return 0;
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[FIND LOCKER]:"+(e-s)+"ms");
		}
	}

	@Override
	public void updateErrorCount(String id, int times) throws Exception {
		long s = System.currentTimeMillis();

		try{
			redisTemplate.opsForValue().set(ERROR+"_"+id,
					""+times,
					config.getSessionTimeout(),
					TimeUnit.MILLISECONDS);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[FIND ERROR]:"+(e-s)+"ms");
		}
	}

	@Override
	public void deleteErrorCount(String id) {
		long s = System.currentTimeMillis();

		try{
			redisTemplate.delete(ERROR+"_"+id);
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[DELETE ERROR]:"+(e-s)+"ms");
		}
	}

	@Override
	public int findErrorCount(String id) throws Exception {
		long s = System.currentTimeMillis();

		try{
			String value = redisTemplate.opsForValue().get(ERROR+"_"+id);
			if(value == null){
				return 0;
			}else{
				int t = Integer.parseInt(value);
				return t;
			}
		} catch (NumberFormatException e) {
			return 0;
		}finally{
			long e = System.currentTimeMillis();
			logger.trace("ELAPSE[FIND ERROR]:"+(e-s)+"ms");
		}
	}

}
