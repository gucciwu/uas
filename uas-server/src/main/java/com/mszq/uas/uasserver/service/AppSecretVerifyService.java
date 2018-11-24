package com.mszq.uas.uasserver.service;

import com.mszq.uas.basement.CODE;
import com.mszq.uas.uasserver.dao.mapper.AppMapper;
import com.mszq.uas.uasserver.dao.model.App;
import com.mszq.uas.uasserver.exception.AppSecretMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppSecretVerifyService {
    @Autowired
    private AppMapper appMapper;

    private Map<Long, String> appSecrets;

    //刷新IP列表指令
    private static final String REFRESH_APP_LIST = "/config/refresh_app_list";

    public void refreshAppList(){
        List<App> list = appMapper.selectAll();
        appSecrets = new HashMap<Long,String>();
        for(App app:list){
            appSecrets.put(app.getId(),app.getSecret());
        }
    }

    public void verifyAppSecret(long appId, String secret) throws AppSecretMatchException {
        if(appSecrets == null){
            refreshAppList();
        }

        String _secret = appSecrets.get(appId);
        if(_secret != null && _secret.equals(secret)){
            //
        }else{
            throw new AppSecretMatchException();

        }
    }
}
