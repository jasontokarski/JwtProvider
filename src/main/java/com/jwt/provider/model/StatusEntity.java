package com.jwt.provider.model;

public class StatusEntity {
    private String status;
    private int majorCode;
    private int minorCode;
    
    public String getStatus() {
        return status;
    }

    public StatusEntity() {
    }

    public StatusEntity(String status, int majorCode, int minorCode) {
        this.status = status;
        this.majorCode = majorCode;
        this.minorCode = minorCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(int majorCode) {
        this.majorCode = majorCode;
    }

    public int getMinorCode() {
        return minorCode;
    }

    public void setMinorCode(int minorCode) {
        this.minorCode = minorCode;
    }
}
