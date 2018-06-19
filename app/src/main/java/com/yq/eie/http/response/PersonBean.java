package com.yq.eie.http.response;

import java.io.Serializable;

public class PersonBean implements Serializable {

    private static final long serialVersionUID = 2654100891340993137L;
    /**
     * alt : https://movie.douban.com/celebrity/1274628/
     * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452260519.76.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452260519.76.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452260519.76.webp"}
     * name : 井柏然
     * id : 1274628
     */

    private String alt;
    private AvatarsBean avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public AvatarsBean getAvatars() {
        return avatars;
    }

    public void setAvatars(AvatarsBean avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class AvatarsBean implements Serializable {

        private static final long serialVersionUID = 8222192230320463001L;
        /**
         * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452260519.76.webp
         * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452260519.76.webp
         * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452260519.76.webp
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
}
