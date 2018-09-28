package com.app.peppermint.entity;

import java.util.ArrayList;

public class PhotoItemEntity {
    private Integer urlId;
    private Integer tipId;
    private ArrayList<PhotoExtendEntity> extend;

    public PhotoItemEntity() {

    }

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

    public ArrayList<PhotoExtendEntity> getExtend() {
        return extend;
    }

    public void setExtend(ArrayList<PhotoExtendEntity> extend) {
        this.extend = extend;
    }

    public PhotoItemEntity(Integer urlId) {
        this.urlId = urlId;
    }
}
