package com.mszq.uas.uasserver.service;

import org.springframework.stereotype.Service;

@Service
public class ConvertOrgIdService {

    public long convert(long originalOrgId, short originalOrgType, short targetOrgType){
        return originalOrgId;
    }
}
