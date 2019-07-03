package com.mszq.platform.base;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Tree implements java.io.Serializable,Comparable {

    private static final long serialVersionUID = 980682543891282923L;
    private Long id;
    private String text;
    private String state = "open";// open,closed
    private boolean checked = false;
    private Integer sort;
    private Object attributes;
    @JsonInclude(Include.NON_NULL)
    private List<Tree> children; // null不输出
    private String iconCls;
    private Long pid;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    public void setState(Integer opened) {
        this.state = (null != opened && opened == 1) ? "open" : "closed";
    }
    
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
		Tree sdto = (Tree)o;
		Integer other = sdto.getSort();
		return this.sort.compareTo(other);
	}

}
