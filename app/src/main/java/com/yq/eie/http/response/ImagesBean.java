package com.yq.eie.http.response;

import java.io.Serializable;

public class ImagesBean implements Serializable {

    private static final long serialVersionUID = 1492329456816751454L;
    /**
     * small : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2519994468.webp
     * large : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2519994468.webp
     * medium : https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2519994468.webp
     */

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}