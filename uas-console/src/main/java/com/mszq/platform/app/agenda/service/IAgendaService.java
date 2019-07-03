package com.mszq.platform.app.agenda.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.mszq.platform.app.agenda.dto.AgendaDTO;
import com.mszq.platform.entity.Agenda;


public interface IAgendaService {

	int create(AgendaDTO AgendaDTO) throws ParseException;


	Map<String, Integer> getAdvanceByRemindTime(String remindTime, String start) throws ParseException;

	int update(AgendaDTO AgendaDTO) throws Exception;

	int delete(long id);


	AgendaDTO getAgendaDTOByKey(long id) throws ParseException;

	List<Agenda> getAllList(Map<String, Object> params);


	List<Agenda> getList(Map<String, Object> params);


}
