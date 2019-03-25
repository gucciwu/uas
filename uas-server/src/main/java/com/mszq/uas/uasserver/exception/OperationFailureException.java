package com.mszq.uas.uasserver.exception;

import com.mszq.uas.uasserver.bean.Response;

public class OperationFailureException extends Exception {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
