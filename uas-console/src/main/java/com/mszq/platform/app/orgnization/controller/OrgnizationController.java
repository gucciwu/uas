package com.mszq.platform.app.orgnization.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszq.platform.app.orgnization.service.IOrgnizationService;
import com.mszq.platform.base.CustomResult;
import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.Orgnization;
import com.mszq.platform.util.ComboOption;

@Controller
@RequestMapping("/orgnization")
public class OrgnizationController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOrgnizationService orgnizationService;

	@RequestMapping(value = "/list")
	@ResponseBody//将内容或对象作为 HTTP 响应正文返回，并调用适合HttpMessageConverter的Adapter转换对象，写入输出流。Spring默认的json协议解析由Jackson完成
	public EUDataGridResult list() throws Exception {
		EUDataGridResult res = orgnizationService.queryAll();
		return res;
	}
	
	@RequestMapping(value = "/tree")
    @ResponseBody
    public List<Tree> tree() {
        return orgnizationService.getOrgnizationTree();
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult addOrgnization(Orgnization record) {
		CustomResult result = null;
		int count = orgnizationService.insert(record);
		if (count == 1) {
			result = CustomResult.ok("添加成功！");
		} else {
			result = CustomResult.error("添加失败！");
		}
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult updateOrgnization(Orgnization record) {
		int emp = orgnizationService.updateByPrimaryKey(record);
		if (emp > 0) {
			return CustomResult.ok("修改成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("修改异常！");
		}
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public CustomResult deleteOrgnizations(String[] ids) {
		int children = orgnizationService.getChildrenBykey(ids);
		if(children > 0){
			return CustomResult.error("删除的数据中包含子节点，请先删除子节点！");
		}
		
		int emp = orgnizationService.deleteBatch(ids);
		if (emp > 0) {
			return CustomResult.ok("删除成功！");
		} else if (emp == 0) {
			return CustomResult.error("数据不存在！");
		} else {
			return CustomResult.error("删除异常！");
		}
	}
	@RequestMapping(value = "/combo")
    @ResponseBody
    public List<ComboOption> ComboOption(String orgName) {
        return orgnizationService.ComboOption(orgName);
    }
}
