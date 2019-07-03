package com.mszq.platform.app.dictionary.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mszq.platform.app.dictionary.dao.IDictionaryDAO;
import com.mszq.platform.app.dictionary.dto.DictionaryWebDTO;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Dictionary;
import com.mszq.platform.entity.DictionaryItem;
import com.mszq.platform.util.GlobalConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImpl implements IDictionaryService{
	@Resource
	private IDictionaryDAO dictionaryDAO;
	
	/*private List<DictionaryWebDTO> dictionaryWebDTOList=new ArrayList<DictionaryWebDTO>();
	private List<Dictionary> listDic;
	List<DictionaryItem> listDicItem;
	private DictionaryWebDTO temp=null;
	private Dictionary dicTemp=null;
	private int count=0;*/

	/**
	 * 分页查询主表数据
	 */
	@Override
	public Object getList(int page, int pageSize,String source) {
		List<DictionaryWebDTO> dictionaryWebDTOList=new ArrayList<DictionaryWebDTO>();
		DictionaryWebDTO temp=null;
		//分页或者不分页
		if(page!=-1&&pageSize!=-1){// 分页处理	
			EUDataGridResult result = new EUDataGridResult();//返回值对象
			PageHelper.startPage(page, pageSize);
			List<Dictionary> list = dictionaryDAO.queryFirstLevel();
			//将dictiona类转换为DTO,向前台传数据
			for(Dictionary dic:list){
				temp=new DictionaryWebDTO(dic.getId(),dic.getName(),"",dic.getStatus(),"open",dic.getParentId(),0,dic.getLevelCode());
				//如果从新建数据字典功能发出的请求，则不去考虑子表是否有数据
				if(source!=null&&source.equalsIgnoreCase("new")){
					if(dic.getIsLeaf()==0) temp.setState("closed");//主表非叶子节点
				}else if(dic.getIsLeaf()==0||dic.getHasItem()==1) temp.setState("closed");//主表非叶子节点或者是有子表数据
				
				dictionaryWebDTOList.add(temp);
			}
			// 取记录总条数
			PageInfo<Dictionary> pageInfo = new PageInfo<Dictionary>(list);
			// 创建一个返回值对象
			result.setRows(dictionaryWebDTOList);
			result.setTotal(pageInfo.getTotal());
			return result;
		}else{//不做分页处理
			List<Dictionary> list = dictionaryDAO.queryFirstLevel();
			//将dictiona类转换为DTO,向前台传数据
			for(Dictionary dic:list){
				temp=new DictionaryWebDTO(dic.getId(),dic.getName(),"",dic.getStatus(),"open",dic.getParentId(),0,dic.getLevelCode());
				//如果从新建数据字典功能发出的请求，则不去考虑子表是否有数据
				if(source!=null&&source.equalsIgnoreCase("new")){
					if(dic.getIsLeaf()==0) temp.setState("closed");//主表非叶子节点
				}else if(dic.getIsLeaf()==0||dic.getHasItem()==1) temp.setState("closed");//主表非叶子节点或者是有子表数据
				dictionaryWebDTOList.add(temp);
			}
			return dictionaryWebDTOList;
		}
		
		
		
	}
	
	/**子数据包含了主表中的子数据，也包含了子表中的数据
	 * @param id:父节点id
	 * @param  source:"new",代表是从新建数据字典功能发出的请求，此时仅返回主表数据即可
	 */
	@Override
	public Object getChildren(int id,String source) {
		List<DictionaryWebDTO> dictionaryWebDTOList=new ArrayList<DictionaryWebDTO>();
		DictionaryWebDTO temp=null;
		List<DictionaryItem> listDicItem;
		Map<String,Object> params=new HashMap<String,Object>();
		
		//先看主表是否有数据
		params.put("parentId", id);
		List<Dictionary> listDic = dictionaryDAO.queryChildren(params);
		//将dictiona类转换为DTO,向前台传数据
		for(Dictionary dic:listDic){
			temp=new DictionaryWebDTO(dic.getId(),dic.getName(),"",dic.getStatus(),"open",dic.getParentId(),0,dic.getLevelCode());//默认是叶子节点
			//如果从新建数据字典功能发出的请求，则不去考虑子表是否有数据
			if(source!=null&&source.equalsIgnoreCase("new")){
				if(dic.getIsLeaf()==0) temp.setState("closed");//主表非叶子节点
			}else if(dic.getIsLeaf()==0||dic.getHasItem()==1) temp.setState("closed");//主表非叶子节点或者是有子表数据
			dictionaryWebDTOList.add(temp);
		}
		//再看子表是否有数据
		if((source==null||!source.equalsIgnoreCase("new"))){
			listDicItem=getDictionaryItemByDicId(id);
			for(DictionaryItem item:listDicItem){
				temp=new DictionaryWebDTO(item.getId(),item.getName(),item.getValue(),item.getStatus(),"open",id,1,"");
				dictionaryWebDTOList.add(temp);
			}
		}
		
		return dictionaryWebDTOList;
	}

	@Override
	public List<DictionaryItem> getDictionaryItemByDicId(int dicId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("dicId",dicId);
		return dictionaryDAO.getDictionaryItemByDicId(params);
	}

	@Override
	public int updateDictionary(Dictionary dic) {
		int count = dictionaryDAO.updateDictionary(dic);
		GlobalConfig.loadAllDictionary();
		return count;
	}
   
	@Override
	public int updateDictionaryItem(DictionaryItem dicItem) {
		int count = dictionaryDAO.updateDictionaryItem(dicItem);
		GlobalConfig.loadAllDictionary();
		return count;
	}

	@Override
	public int delete(int id, int type,String levelCode) {
        int result=0;
        Dictionary dicTemp=null;
		int count=0;
		if(type==0){//主表,需要删除所有的下级数据字典以及这些下级字典的对应的子表数据
			//先删除子表中的数据.id是主表数据，这个数据有可能有多个子节点
			result=dictionaryDAO.deleteDictionaryItemByDicLevelCode(levelCode);
			//再删除主表，要删除主表及主表子节点
			result=dictionaryDAO.deleteDictionaryByLevelCode(levelCode);
			
		}else if(type==1){//子表
			//看子表中是否还有别的相同dicId的数据，如果有，则不修改主表的hasItem字段，如果没有，则需要修改主表的hasItem字段为0
			count=dictionaryDAO.getDictionaryItemCountById(id);
			if(count==1){
				dicTemp=dictionaryDAO.queryDictionaryByDictionaryItemId(id);
				if(dicTemp!=null) {
					dicTemp.setHasItem(0);
					result=dictionaryDAO.updateDictionary(dicTemp);
				}
				
			}
			//删除子表数据
			result=dictionaryDAO.deleteDictionaryItemById(id);
		}else{
			result=-1;
		}
		GlobalConfig.loadAllDictionary();
		return result;
	}

	@Override
	public int createDictionary(Dictionary dic) {
		int result=0;	
		Dictionary dicTemp=null;
		int count=0;
		//先查询父节点的level_code
		dicTemp=dictionaryDAO.getDictionaryById(dic.getParentId());
		if(dicTemp!=null&&dicTemp.getLevelCode()!=null&&!dicTemp.getLevelCode().equalsIgnoreCase("")) dic.setLevelCode(dicTemp.getLevelCode());//如果为空，则代表是一级数据字典，其levelcode需要在插入后再重新设置一次
		else dic.setLevelCode("");
		dic.setStatus(1);
		//TODO 设置创建人ID
		dic.setCreatorId(1);
		dic.setIsLeaf(1);
		dic.setHasItem(0);
		
		result=dictionaryDAO.createDictionary(dic);
		if(dicTemp!=null){
			//设计父字典的isLeaf属性为0
			dicTemp.setIsLeaf(0);
			dictionaryDAO.updateDictionary(dicTemp);
			//设置子数据的levelcode
			dic.setLevelCode(dic.getLevelCode()+dic.getId());
			dictionaryDAO.updateDictionary(dic);
		}
		else{
			dic.setLevelCode(Integer.toString(dic.getId()));
			dictionaryDAO.updateDictionary(dic);
		}
		GlobalConfig.loadAllDictionary();
		return result;
	}

	@Override
	public int createDictionaryItem(DictionaryItem dicItem) {
		int result=0;
		Dictionary dicTemp=null;
		//先插入子表数据
		result=dictionaryDAO.createDictionaryItem(dicItem);
		//然后再修改主表数据的hasItem属性
		dicTemp=dictionaryDAO.getDictionaryById(dicItem.getDicId());
		if(dicTemp!=null) {
			dicTemp.setHasItem(1);
			result=dictionaryDAO.updateDictionary(dicTemp);
		}
		GlobalConfig.loadAllDictionary();
		return result;
	}

	/**
	 * 根据主表的code查询所有的子表数据
	 */
	@Override
	public List<DictionaryItem> getDictionaryItemByDicCode(String dicCode) {
		return dictionaryDAO.getDictionaryItemByDicCode(dicCode);
	}

	/**
	 * 根据子表的id获取子表的值
	 */
	@Override
	public DictionaryItem getDictionaryItemById(long id) {
		return dictionaryDAO.getDictionaryItemById(id);
	}

	@Override
	public List<Dictionary> queryAll(int parentId,int status) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("parentId", new Integer(parentId));
        if(status!=-1) params.put("status",new Integer(status));//在字典管理模块中要获取所哟的数据，提供给其他模块使用时，则只需要返回启用的数据
		//1、先查出主表数据，有可能是多级的数据结构
		List<Dictionary> listDic=dictionaryDAO.queryAll(params);
		//2.再查出每个主表树相应的子表数据
		if(listDic!=null&&listDic.size()>0){
			for(Dictionary dictionary:listDic){
				getDictionaryItem(dictionary);
			}
		}
		return listDic;
	}
	
	private void getDictionaryItem(Dictionary parentDictionary){
		List<Dictionary> childDictionary;
		 Map<String,Object> params=new HashMap<String,Object>();
	    
		
		if(parentDictionary.getIsLeaf()==1&&parentDictionary.getHasItem()==1){//查找子表数据
			params.put("dicId", parentDictionary.getId());
			params.put("status",1);
			List<DictionaryItem> listDicItem=dictionaryDAO.getDictionaryItemByDicId(params);
			parentDictionary.setChildDicItem(listDicItem);
		}else{
			 params.put("parentId",parentDictionary.getId());
			 params.put("status", 1);
			childDictionary=dictionaryDAO.queryChildren(params);
			if(childDictionary!=null&&childDictionary.size()>0){
				parentDictionary.setChildDictionary(childDictionary);
				for(Dictionary temp:childDictionary){
					getDictionaryItem(temp);
				}
			}
			
			
		}
	}

}
