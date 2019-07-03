package com.mszq.platform.app.dictionary.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.dictionary.dto.DictionaryWebDTO;
import com.mszq.platform.app.dictionary.service.IDictionaryService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.entity.Dictionary;
import com.mszq.platform.entity.DictionaryItem;
import com.mszq.platform.util.ComboOption;
import com.mszq.platform.util.ComboTreeOption;
import com.mszq.platform.util.GlobalConfig;

@Controller
@RequestMapping(value="/dictionary")
public class DictionaryController {
	private final Logger logger=LoggerFactory.getLogger(DictionaryController.class);
	@Resource
	IDictionaryService dictionaryService;
	/*private Dictionary dic;
	private DictionaryItem dicItem;*/
	
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Object list(String id,String source,Integer page, Integer rows, HttpServletResponse response){
		dictionaryService.queryAll(0,-1);
		//这是加载子节点的请求
		if(null!=id&&!"".equalsIgnoreCase(id)){
			return dictionaryService.getChildren(Integer.parseInt(id),source);//直接返回一个list,springmv会将这个list转换成json字符串
		}else{//加载主表一级节点
			return dictionaryService.getList(page==null?-1:page, rows==null?-1:rows,source);//返回一个分页对象
		}
		
		
	}
	
	
	/**
	 * 新增加一个数据字典主表 or 子表
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/create",method=RequestMethod.POST)
	@ResponseBody
	public CustomResult create(DictionaryWebDTO dto){
		int result = 0;
		int type=dto.getType();
		Dictionary dic;
		DictionaryItem dicItem;
		
		if(type==0){//创建主表数据
			dic=new Dictionary(dto.getName(),dto.getCode(),dto.getParentId());
			result=dictionaryService.createDictionary(dic);
		}else{//创建子表数据
			//TODO 创建人的id要修改哈
			dicItem=new DictionaryItem(0,dto.getParentId(),dto.getName(),dto.getValue(),1,0);//id设置了一个值，仅为调用不同的构造函数，没有实际意义
			result=dictionaryService.createDictionaryItem(dicItem);			
		}
		
		if (result > 0) {
			return CustomResult.ok("新增操作成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("新增操作异常！");
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public CustomResult update(DictionaryWebDTO dto){
		int result = 0;
		int type=dto.getType();
		int status=dto.getStatus();
		Dictionary dic;
		DictionaryItem dicItem;
		
		if(type==0){//更新主表数据
			dic=new Dictionary(dto.getId(),dto.getName(),status);
			result=dictionaryService.updateDictionary(dic);
		}else{//更新子表数据
			dicItem=new DictionaryItem(dto.getId(),dto.getName(),dto.getValue(),status);
			result=dictionaryService.updateDictionaryItem(dicItem);
		}
		
		if (result > 0) {
			return CustomResult.ok("修改成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("修改异常！");
		}
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult delete(int id,int type,String levelCode) {
		int result =dictionaryService.delete(id,type,levelCode);
		if (result > 0) {
			return CustomResult.ok("删除成功！");
		} else if (result == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	
	@RequestMapping(value = "/comboOption")
	@ResponseBody
	public List<ComboOption> getOptions(String key) {
		return GlobalConfig.getDictionaryValue(key);
	}
	@RequestMapping(value = "/comboTreeOption")
	@ResponseBody
	public List<ComboTreeOption> getTreeOptions(String key) {
		String[] keys= key.split(",");
		List<ComboTreeOption> combo =new ArrayList<>();
		for(int i=0;i<keys.length;i++){
			ComboTreeOption comboTreeOption=new ComboTreeOption();
			Dictionary dictionary =GlobalConfig.getDictionaryByKey(keys[i]);
			if(dictionary==null){
				continue;
			}
			comboTreeOption.setText(dictionary.getName());
			comboTreeOption.setId(keys[i]);
			comboTreeOption.setPid("0");
			combo.add(comboTreeOption);
			List<ComboTreeOption> children =new ArrayList<>();
			comboTreeOption.setChildren(children);
			List<ComboOption> comboOptions = GlobalConfig.getDictionaryValue(keys[i]);
			for(ComboOption comboOption:comboOptions){
				 comboTreeOption=new ComboTreeOption();
				comboTreeOption.setText(comboOption.getText());
				comboTreeOption.setId(comboOption.getValue());
				comboTreeOption.setPid(keys[i]);
				children.add(comboTreeOption);
			}
		}
		return combo;
	}
	
}
