package com.mszq.uas.sso.bean;


public class ExRequest extends Request {
    private String _secret;

    public String get_secret() {
        return _secret;
    }

    public void set_secret(String _secret) {
        this._secret = _secret;
    }

}
