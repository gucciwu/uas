package com.mszq.platform.app.orgnization.service;


import java.util.List;
import java.util.Map;

import com.mszq.platform.base.EUDataGridResult;
import com.mszq.platform.base.Tree;
import com.mszq.platform.entity.Orgnization;
import com.mszq.platform.util.ComboOption;

public interface IOrgnizationService {
	public EUDataGridResult queryAll();
	public EUDataGridResult queryByParams(Map<String,String> params,int page, int pageSize) ;
	public List<Tree> getOrgnizationTree();
	public List<Long> getAllChildren(Long parentId);
	public int insert(Orgnization record);
	public int deleteBatch(String[] ids);
	public int deleteBatchByFids(String[] ids);
    public int updateByPrimaryKey(Orgnization record);
    public int updateByRegisterCode(Orgnization record);
    public int getChildrenBykey(String[] ids);
	List<Orgnization> selectByName(String name);
	List<Orgnization> selectByRegisterCode(String registerCode);
	List<Orgnization> queryAllChildOrgnization(long parentOrgnizationId);
	public Orgnization queryRootOrgnization(long curentOrgnizationId);
	public Long checkOrgnizationByNameOrRegisterCode(String registerCode);
	public List<ComboOption> ComboOption(String orgName);
}
