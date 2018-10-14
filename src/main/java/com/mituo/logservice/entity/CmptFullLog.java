package com.mituo.logservice.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "api_full_log")
public class CmptFullLog {

    @Id
    private String idCmptFullLog;

    private String idApi;
    private String idApiGroup;
    private String appID;
    private Date requestTime;
    private String requestContentType;
    private String requestCharset;
    private String requestPath;
    private String requestParams;
    private String requestPostParams;
    private String requestHost;
    private String requestBody;
    private int requestBodySize;
    private String requestMethod;
    private String requestClientIp;
    private int requestPort;
    private String requestHeaders;
    private String apiName;
    private String appName;
    private String apiGroupName;
    private String appDescription;
    private Date appCreateTime;
    private Date responseTime;
    private int responseStateCode;
    private String responseErrorType;
    private String responseCharset;
    private String responseErrorInfo;
    private String responseBody;
    private int responseBodySize;
    private String responseMediaType;
    private String responseHeaders;

    public String getIdCmptFullLog() {
        return idCmptFullLog;
    }

    public void setIdCmptFullLog(String idCmptFullLog) {
        this.idCmptFullLog = idCmptFullLog;
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
        return this.idCmptFullLog.equals(that.idCmptFullLog);
    }

    @Override
    public int hashCode() {
        return this.idCmptFullLog.hashCode();
    }
}
