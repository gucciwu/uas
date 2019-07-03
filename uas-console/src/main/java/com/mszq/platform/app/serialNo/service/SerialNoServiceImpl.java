package com.mszq.platform.app.serialNo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszq.platform.app.serialNo.dao.SerialNoDAO;
import com.mszq.platform.app.serialNo.dto.SerialNoDto;
import com.mszq.platform.util.Constants;
import com.mszq.platform.util.DateUtil;

@Service
public class SerialNoServiceImpl implements SerialNoService {

    @Autowired
    private SerialNoDAO dao;

    @Override
    public String getSerialNo(String code, String busiDate) {
        SerialNoDto dto = new SerialNoDto(code, busiDate);
        dao.getSerialNo(dto);
        return dto.getResult();
    }

    @Override
    public String getCode(String code, String busiDate) {
        return code + busiDate + getSerialNo(code, busiDate);
    }

    @Override
    public String getSerialNo(String code, Date busiDate) {
        SerialNoDto dto = new SerialNoDto(code, DateUtil.dateToStr(busiDate, Constants.PATTERN_DATE_NO_SYMBOL));
        dao.getSerialNo(dto);
        return dto.getResult();
    }

    @Override
    public String getCode(String code, Date busiDate) {
        return code + DateUtil.dateToStr(busiDate, Constants.PATTERN_DATE_NO_SYMBOL) + getSerialNo(code, busiDate);
    }

    @Override
    public int insert(Long id, String code) {
        return dao.insert(id, code);
    }

}
