package com.mszq.platform.app.agenda.dao;

import java.util.List;
import java.util.Map;

import com.mszq.platform.entity.Agenda;


public interface IAgendaDAO {

    int deleteByPrimaryKey(Long id);

    int insert(Agenda record);

    Agenda selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Agenda record);

    int updateByPrimaryKey(Agenda record);

	List<Agenda> queryAll(Map<String, Object> params);

	List<Agenda> getproductCalendarList(Long userID);

	List<Agenda> queryUserAll(Map<String, Object> params);
}