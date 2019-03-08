package com.mszq.uas.uasserver.bean;

import com.mszq.uas.uasserver.bean.Response;
import io.swagger.annotations.ApiModelProperty;

public class UpdateOrgResponse extends Response {
    private long innerOrgId;

    public long getInnerOrgId() {
        return innerOrgId;
    }

    public void setInnerOrgId(long innerOrgId) {
        this.innerOrgId = innerOrgId;
    }
}
