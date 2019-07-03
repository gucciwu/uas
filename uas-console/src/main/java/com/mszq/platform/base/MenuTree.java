package com.mszq.platform.base;

import java.util.List;


public class MenuTree implements java.io.Serializable,Comparable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long pid;
    private String title;
    private String icon ;
    private String href;
    private Integer sort;
    private boolean isCurrent = false;
    private List<MenuTree> children; // null不输出
    /**
     * ajax,iframe,
     */
    private String openMode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public List<MenuTree> getChildren() {
		return children;
	}
	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	public String getOpenMode() {
		return openMode;
	}
	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public int compareTo(Object o) {
		MenuTree sdto = (MenuTree) o;
		int other = sdto.getSort();
		return this.sort.compareTo(other);
	}


}
