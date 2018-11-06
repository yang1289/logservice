package com.mituo.logservice.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ag_full_log")
public class CmptFullLog {
    @Id
    @Column(name = "idcmptfulllog",nullable = false,unique = true,length = 128)
    private String idcmptfulllog;

    @Column(name = "idapi",nullable = false)
    private String idApi;
    @Column(name = "idapigroup",nullable = false)
    private String idApiGroup;
    @Column(name = "appid")
    private String appID;
    @Column(name="requesttime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestTime;
    @Column(name = "requestcontenttype")
    private String requestContentType;
    @Column(name = "requestcharset")
    private String requestCharset;
    @Column(name = "requestpath")
    private String requestPath;
    @Column(name = "requestparams")
    private String requestParams;
    @Column(name = "requestpostparams")
    private String requestPostParams;
    @Column(name = "requesthost")
    private String requestHost;
    @Column(name = "requestbody")
    private String requestBody;
    @Column(name = "requestbodysize")
    private int requestBodySize;
    @Column(name = "requestmethod")
    private String requestMethod;
    @Column(name = "requestclientip")
    private String requestClientIp;
    @Column(name = "requestport")
    private int requestPort;
    @Column(name = "requestheaders")
    private String requestHeaders;
    @Column(name = "apiname")
    private String apiName;
    @Column(name = "appname")
    private String appName;
    @Column(name = "apigroupname")
    private String apiGroupName;
    @Column(name = "appdescription")
    private String appDescription;
    @Column(name = "appcreatetime")
    private Date appCreateTime;
    @Column(name = "responsetime")
    private Date responseTime;
    @Column(name = "responsestatecode")
    private int responseStateCode;
    @Column(name = "responseerrortype")
    private String responseErrorType;
    @Column(name = "responsecharset")
    private String responseCharset;
    @Column(name = "responseerrorinfo")
    private String responseErrorInfo;
    @Column(name = "responsebody")
    private String responseBody;
    @Column(name = "responsebodysize")
    private int responseBodySize;
    @Column(name = "responsemediatype")
    private String responseMediaType;
    @Column(name = "responseheaders")
    private String responseHeaders;

    public String getIdcmptfulllog() {
        return idcmptfulllog;
    }

    public void setIdcmptfulllog(String idcmptfulllog) {
        this.idcmptfulllog = idcmptfulllog;
    }

    public String getIdApi() {
        return idApi;
    }

    public void setIdApi(String idApi) {
        this.idApi = idApi;
    }

    public String getIdApiGroup() {
        return idApiGroup;
    }

    public void setIdApiGroup(String idApiGroup) {
        this.idApiGroup = idApiGroup;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public void setRequestContentType(String requestContentType) {
        this.requestContentType = requestContentType;
    }

    public String getRequestCharset() {
        return requestCharset;
    }

    public void setRequestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestPostParams() {
        return requestPostParams;
    }

    public void setRequestPostParams(String requestPostParams) {
        this.requestPostParams = requestPostParams;
    }

    public String getRequestHost() {
        return requestHost;
    }

    public void setRequestHost(String requestHost) {
        this.requestHost = requestHost;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public int getRequestBodySize() {
        return requestBodySize;
    }

    public void setRequestBodySize(int requestBodySize) {
        this.requestBodySize = requestBodySize;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestClientIp() {
        return requestClientIp;
    }

    public void setRequestClientIp(String requestClientIp) {
        this.requestClientIp = requestClientIp;
    }

    public int getRequestPort() {
        return requestPort;
    }

    public void setRequestPort(int requestPort) {
        this.requestPort = requestPort;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApiGroupName() {
        return apiGroupName;
    }

    public void setApiGroupName(String apiGroupName) {
        this.apiGroupName = apiGroupName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public Date getAppCreateTime() {
        return appCreateTime;
    }

    public void setAppCreateTime(Date appCreateTime) {
        this.appCreateTime = appCreateTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public int getResponseStateCode() {
        return responseStateCode;
    }

    public void setResponseStateCode(int responseStateCode) {
        this.responseStateCode = responseStateCode;
    }

    public String getResponseErrorType() {
        return responseErrorType;
    }

    public void setResponseErrorType(String responseErrorType) {
        this.responseErrorType = responseErrorType;
    }

    public String getResponseCharset() {
        return responseCharset;
    }

    public void setResponseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
    }

    public String getResponseErrorInfo() {
        return responseErrorInfo;
    }

    public void setResponseErrorInfo(String responseErrorInfo) {
        this.responseErrorInfo = responseErrorInfo;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getResponseBodySize() {
        return responseBodySize;
    }

    public void setResponseBodySize(int responseBodySize) {
        this.responseBodySize = responseBodySize;
    }

    public String getResponseMediaType() {
        return responseMediaType;
    }

    public void setResponseMediaType(String responseMediaType) {
        this.responseMediaType = responseMediaType;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final CmptFullLog that = (CmptFullLog)o;
        return this.idcmptfulllog.equals(that.idcmptfulllog);
    }

    @Override
    public int hashCode() {
        return this.idcmptfulllog.hashCode();
    }
}
