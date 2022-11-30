package com.waracle.cakemgr.model;

import com.waracle.cakemgr.entity.CakeEntity;


public class CakeSaveResponse {
    private String status;
    private String statusCode;
    private CakeEntity cakeEntity;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public CakeEntity getCakeEntity() {
        return cakeEntity;
    }

    public void setCakeEntity(CakeEntity cakeEntity) {
        this.cakeEntity = cakeEntity;
    }
}
