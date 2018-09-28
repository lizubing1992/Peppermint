package com.app.peppermint.entity;

public class PhotoExtendEntity {
    private Integer urlId;
    private Integer tipId;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public Integer getTipId() {
        return tipId;
    }

    public void setTipId(Integer tipId) {
        this.tipId = tipId;
    }

    public PhotoExtendEntity(Integer urlId) {
        this.urlId = urlId;
    }

    public PhotoExtendEntity() {
    }

    public PhotoExtendEntity(Integer urlId, Integer tipId) {
        this.urlId = urlId;
        this.tipId = tipId;
    }
}

