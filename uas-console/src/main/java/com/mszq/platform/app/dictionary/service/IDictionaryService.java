package com.mszq.platform.app.dictionary.service;


import java.util.List;

import com.mszq.platform.app.dictionary.dto.DictionaryTreeDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Dictionary;
import com.mszq.platform.entity.DictionaryItem;


public interface IDictionaryService {
	public List<Dictionary> queryAll(int parentId,int status);
	public Object getList(int page, int pageSize,String source) ;
	public Object getChildren(int id,String source);
	public List<DictionaryItem>  getDictionaryItemByDicId(int dicId);
	public List<DictionaryItem>  getDictionaryItemByDicCode(String dicCode);
	public DictionaryItem  getDictionaryItemById(long id);
	public int updateDictionary(Dictionary dic);
	public int updateDictionaryItem(DictionaryItem dicItem);
	public int delete(int id,int type,String levelCode);
	public int createDictionary(Dictionary dic);
	public int createDictionaryItem(DictionaryItem dicItem);

}
