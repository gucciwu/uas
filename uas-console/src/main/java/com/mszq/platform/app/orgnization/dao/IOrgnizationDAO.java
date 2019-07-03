package com.mszq.platform.app.orgnization.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.entity.Orgnization;
import com.mszq.platform.util.ComboOption;

@MapperScan
public interface IOrgnizationDAO {
	
	public List<Orgnization> queryAll();
	public List<Orgnization> queryByParams(Map<String,String> params);

	public int insert(Orgnization record);

	public int deleteBatch(String[] ids);
	public int deleteBatchByFids(String[] ids);

	public int updateByPrimaryKey(Orgnization record);
	public int updateByRegisterCode(Orgnization record);
	public int saveOrUpdate(Orgnization record);
	
	public int getChildrenBykey(String[] ids);

	public Orgnization selectParentOrgnizationById(long id);
	public Orgnization selectById(long id);
	public List<Orgnization> selectByName(String name);

	public List<Orgnization> selectByRegisterCode(String registerCode);
	
	public List<Orgnization> queryAllChildOrgnization(long id);
	public Long checkOrgnizationByNameOrRegisterCode(@Param("registerCode") String registerCode);
	
	public List<ComboOption> queryForCombo(@Param("orgName")String orgName);
}
