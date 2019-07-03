package com.mszq.platform.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mszq.platform.base.Tree;

public class ComboTreeOption {
	
	 private static final long serialVersionUID = 980682543891282923L;
	    private String id;
	    private String text;
	    private String state = "open";// open,closed
	    private boolean checked = false;
	    private Integer sort;
	    private Object attributes;
	    @JsonInclude(Include.NON_NULL)
	    private List<ComboTreeOption> children; // null不输出
	    private String iconCls;
	    private String pid;
	    /**
	     * ajax,iframe,
	     */
	    private String openMode;

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
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

	    public List<ComboTreeOption> getChildren() {
	        return children;
	    }

	    public void setChildren(List<ComboTreeOption> children) {
	        this.children = children;
	    }

	    public String getIconCls() {
	        return iconCls;
	    }

	    public void setIconCls(String iconCls) {
	        this.iconCls = iconCls;
	    }

	    public String getPid() {
	        return pid;
	    }

	    public void setPid(String pid) {
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
		
		public int compareTo(Object o) {
			ComboTreeOption sdto = (ComboTreeOption)o;
			Integer other = sdto.getSort();
			return this.sort.compareTo(other);
		}

}
