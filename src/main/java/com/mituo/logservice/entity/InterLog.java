package com.mituo.logservice.entity;


import javax.persistence.*;


@Entity
@Table(name = "inter_log")
public class InterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numId;

    private String interfaceTime;
    private String requester;
    private String terminalId;
    private String interfaceCondition;
    private boolean interfaceResult;
    private String username;
    private String sfzh;
    private String requesterId;
    private String sjbbh;

    public Integer getNumId() {
        return numId;
    }

    public void setNumId(Integer numId) {
        this.numId = numId;
    }

    public String getInterfaceTime() {
        return interfaceTime;
    }

    public void setInterfaceTime(String interfaceTime) {
        this.interfaceTime = interfaceTime;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getInterfaceCondition() {
        return interfaceCondition;
    }

    public void setInterfaceCondition(String interfaceCondition) {
        this.interfaceCondition = interfaceCondition;
    }

    public boolean isInterfaceResult() {
        return interfaceResult;
    }

    public void setInterfaceResult(boolean interfaceResult) {
        this.interfaceResult = interfaceResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getSjbbh() {
        return sjbbh;
    }

    public void setSjbbh(String sjbbh) {
        this.sjbbh = sjbbh;
    }
}
