package com.mszq.platform.app.serialNo.dao;

import org.apache.ibatis.annotations.Param;

import com.mszq.platform.app.serialNo.dto.SerialNoDto;

public interface SerialNoDAO {

    void getSerialNo(SerialNoDto dto);

    int insert(@Param("id") Long id, @Param("code") String code);
}
