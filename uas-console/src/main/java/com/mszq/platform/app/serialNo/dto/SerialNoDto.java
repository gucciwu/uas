package com.mszq.platform.app.serialNo.dto;

public class SerialNoDto {
    private String code;

    private String result;

    private String busiDate;

    public SerialNoDto() {
        super();
    }

    public SerialNoDto(String code, String busiDate) {
        super();
        this.code = code;
        this.busiDate = busiDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBusiDate() {
        return busiDate;
    }

    public void setBusiDate(String busiDate) {
        this.busiDate = busiDate;
    }
}
