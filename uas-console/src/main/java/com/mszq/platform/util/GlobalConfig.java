package com.mszq.platform.util;

import com.google.common.collect.Lists;
import com.mszq.platform.app.config.service.ConfigServiceImpl;
import com.mszq.platform.app.dictionary.service.DictionaryServiceImpl;
import com.mszq.platform.base.redis.RedisUtil;
import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.Dictionary;
import com.mszq.platform.entity.DictionaryItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class GlobalConfig {
	private static Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
//    private static List<Dictionary> dictionarys = Lists.newArrayList();
//    private static List<Config> configs = Lists.newArrayList();
    private static String REDIS_GLOBAL_DICTIONARY = "REDIS_GLOBAL_DICTIONARY";
    private static String REDIS_GLOBAL_CONFIG = "REDIS_GLOBAL_CONFIG";

    public synchronized static void loadAllDictionary() {
        logger.info("load all dictionary");
        List<Dictionary> list = (List<Dictionary>) SpringHelper.popBean(DictionaryServiceImpl.class).queryAll(0,1);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("no dictionary");
            return;
        }
//        dictionarys = list;
        SpringHelper.popBean(RedisUtil.class).set(REDIS_GLOBAL_DICTIONARY, list, (long)Integer.MAX_VALUE);
    }
    /**
     * 参数信息加载
     */
    public synchronized static void loadAllConfig() {
        logger.info("load all config");
        List<Config> list = (List<Config>) SpringHelper.popBean(ConfigServiceImpl.class).queryAll();
        if (CollectionUtils.isEmpty(list)) {
            logger.info("no config");
            return;
        }
//        configs = list;
        SpringHelper.popBean(RedisUtil.class).set(REDIS_GLOBAL_CONFIG, list, (long)Integer.MAX_VALUE);
    }
    
    /**
     * 参数信息获取
     * @param key
     * @return
     */
    public static String getConfgiValue(String key) {
        List<Config> configs = getConfigList();
    	for (Config config : configs) {
			if (key.equals(config.getCode())) {
				return config.getValue();
			}
		}
    	logger.info("未找到该配置信息：" + key);
    	return "";
    }
    /**
     * 基本信息缓存数据保存。
     * add by :qsyanga
     * 描述：此功能用于参数数据信息更新：如果存在则直接更新，否则新增
     * @param configinfo
     * @return
     */
    public static boolean setConfigValue(Config configinfo){
    	List<Config> configs = getConfigList();
    	if(configinfo.getCode()==null){
    		return false;
    	}
    	int index =0;
    	for (Config config : configs) {
			if (configinfo.getCode().equals(config.getCode())) {
				configs.set(index, configinfo);
				break;
			}
			index++;
		}
    	if(configs.size()==index){
    		configs.add(configinfo);
    	}
    	 SpringHelper.popBean(RedisUtil.class).set(REDIS_GLOBAL_CONFIG, configs, (long)Integer.MAX_VALUE);
    	return true;
    }
    
    
    @SuppressWarnings("unchecked")
    private static List<Dictionary> getDictionaryList() {
        List<Dictionary> dictionarys = (List<Dictionary>)SpringHelper.popBean(RedisUtil.class).get(REDIS_GLOBAL_DICTIONARY);
        if (dictionarys == null) {
            loadAllDictionary();
            dictionarys = (List<Dictionary>)SpringHelper.popBean(RedisUtil.class).get(REDIS_GLOBAL_DICTIONARY);
        }
        return dictionarys;
    }

    @SuppressWarnings("unchecked")
    private static List<Config> getConfigList() {
        List<Config> configs = (List<Config>)SpringHelper.popBean(RedisUtil.class).get(REDIS_GLOBAL_CONFIG);
        if (configs == null) {
            loadAllConfig();
            configs = (List<Config>)SpringHelper.popBean(RedisUtil.class).get(REDIS_GLOBAL_CONFIG);
        }
        return configs;
    }

    public static List<ComboOption> getDictionaryValue(String key) {
        List<Dictionary> dictionarys = getDictionaryList();
    	List<ComboOption> comboOptions = Lists.newArrayList();
    	findKey(dictionarys,comboOptions,key);
    	return comboOptions;
    }
    
    public static Dictionary getDictionaryByKey(String key){
    	List<Dictionary> dictionarys = getDictionaryList();
    	if(key!=null){
    		return findDictionary(key,dictionarys);
    	}
    	
    	return null;
    }    
    private static Dictionary findDictionary(String key,List<Dictionary> dictionarys){
    	for (Dictionary dictionary : dictionarys) {
			if (key.equals(dictionary.getCode())) {
				return dictionary;
			}else{
				if(dictionary.getChildDictionary()!=null&&dictionary.getChildDictionary().size()>0){					
						return findDictionary(key,dictionary.getChildDictionary());
				}
			}
    	}
    	return null;
    }
    
    
    
    private static void findKey(List<Dictionary> dics,List<ComboOption> comboOptions, String key){
    	if(dics != null){
    		for (Dictionary dictionary : dics) {
    			if (key.equals(dictionary.getCode())) {
    				if (dictionary.getChildDicItem() !=null) {
    					for (DictionaryItem di : dictionary.getChildDicItem()) {
    						ComboOption co = new ComboOption();
    						co.setText(di.getName());
    						co.setValue(di.getValue());
    						comboOptions.add(co);
						}
    					return;
					}
    			}else {
    				findKey(dictionary.getChildDictionary(),comboOptions,key);
				}
    		}
    	}
    }

}
