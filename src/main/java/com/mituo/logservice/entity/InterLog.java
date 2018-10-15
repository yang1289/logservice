package com.mituo.logservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inter_log")
public class InterLog {

    @Id
    private String numId;

    private String interFaceTime;
    private String requester;
    private String terminalId;
    private String interFaceCondition;
    private boolean interFaceResult;
    private String username;
    private String sfzh;
    private String requesterID;
    private String sjbbh;

    public InterLog(String interFaceTime, String requester, String terminalId,
                    String interFaceCondition, boolean interFaceResult, String username,
                    String sfzh, String requesterID, String sjbbh) {
        this.interFaceTime = interFaceTime;
        this.requester = requester;
        this.terminalId = terminalId;
        this.interFaceCondition = interFaceCondition;
        this.interFaceResult = interFaceResult;
        this.username = username;
        this.sfzh = sfzh;
        this.requesterID = requesterID;
        this.sjbbh = sjbbh;
    }

    public InterLog() {
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getInterFaceTime() {
        return interFaceTime;
    }

    public void setInterFaceTime(String interFaceTime) {
        this.interFaceTime = interFaceTime;
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

    public String getInterFaceCondition() {
        return interFaceCondition;
    }

    public void setInterFaceCondition(String interFaceCondition) {
        this.interFaceCondition = interFaceCondition;
    }

    public boolean isInterFaceResult() {
        return interFaceResult;
    }

    public void setInterFaceResult(boolean interFaceResult) {
        this.interFaceResult = interFaceResult;
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

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }

    public String getSjbbh() {
        return sjbbh;
    }

    public void setSjbbh(String sjbbh) {
        this.sjbbh = sjbbh;
    }
}
