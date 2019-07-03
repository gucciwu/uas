package com.mszq.platform.app.dictionary.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mszq.platform.app.dictionary.dto.DictionaryTreeDTO;
import com.mszq.platform.entity.Config;
import com.mszq.platform.entity.Dictionary;
import com.mszq.platform.entity.DictionaryItem;

@MapperScan
public interface IDictionaryDAO {
	public List<Dictionary> queryFirstLevel();
	public List<Dictionary> queryChildren(Map<String,Object> params);
	public Dictionary queryDictionaryByDictionaryItemId(@Param("dicItemId") int dicItemId);
	public Dictionary getDictionaryById(@Param("Id") int id);
	public List<DictionaryItem> getDictionaryItemByDicId(Map<String,Object> params);
	public List<DictionaryItem> getDictionaryItemByDicCode(@Param("dicCode") String dicCode);
	public DictionaryItem getDictionaryItemById(@Param("id") long id);
	public int updateDictionary(Dictionary dic);
	public int updateDictionaryItem(DictionaryItem dicItem);
	//这个方法默认会删除主表中的孩子节点
	public int deleteDictionaryByLevelCode(@Param("levelCode") String levelCode);
	public int deleteDictionaryItemByDicLevelCode(@Param("levelCode") String levelCode);
	public int deleteDictionaryItemById(@Param("id") int id);
	public int createDictionary(Dictionary dic);
	public int createDictionaryItem(DictionaryItem dicItem);
	public int getDictionaryItemCountById(@Param("id") int id);//查询子表中同一个主表的子表记录条数
	
	public List<Dictionary> queryAll(Map<String,Object> params);

}
