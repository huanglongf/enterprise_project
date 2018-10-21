package com.jumbo.webservice.ems.command.New;

public class EmsSystemParam {
    private String sign;// 签名
    private String timestamp;// 时间戳
    private String version;// 版本
    private String method;// 方法名
    private String partner_id;// 接口调用方
    private String format;// 接口参数/响应格式 json/xml
    private String app_key;// 应用ID
    private String charset;// 接口编码默认为UTF-8
    private String authorization;// 非公共接口需要使用
    private String app_secret;

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

}
