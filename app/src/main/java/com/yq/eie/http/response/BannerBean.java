package com.yq.eie.http.response;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BannerBean {

    /**
     * pic : [{"type":11,"mo_type":8,"code":"http://music.baidu.com/playmv/568838779","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1515034339300d780fece1dc3946f10c30a8356279.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=868&columnid=87","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_15149762055838c7cf107a1874522746fb92ba5a36.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=863&columnid=91","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1514975087dc8907d25c16ec6f7de84b022c01dfb1.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":6,"mo_type":4,"code":"http://music.baidu.com/h5pc/spec_detail?id=867&columnid=86","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_15145234213b13f7c48a57c890383a77cc78f0c373.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"},{"type":2,"mo_type":2,"code":"568809143","randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1514960084a88929877767e409da4513585bd67072.jpg","randpic_ios5":"","randpic_desc":"","randpic_ipad":"","randpic_qq":"","randpic_2":"","randpic_iphone6":"","special_type":0,"ipad_desc":"","is_publish":"111111"}]
     * error_code : 22000
     */

    private int error_code;
    private List<PicBean> pic;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class PicBean {
        /**
         * type : 11
         * mo_type : 8
         * code : http://music.baidu.com/playmv/568838779
         * randpic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1515034339300d780fece1dc3946f10c30a8356279.jpg
         * randpic_ios5 :
         * randpic_desc :
         * randpic_ipad :
         * randpic_qq :
         * randpic_2 :
         * randpic_iphone6 :
         * special_type : 0
         * ipad_desc :
         * is_publish : 111111
         */

        private int type;
        private int mo_type;
        private String code;//链接
        private String randpic;//展示图片链接
        private String randpic_ios5;
        private String randpic_desc;
        private String randpic_ipad;
        private String randpic_qq;
        private String randpic_2;
        private String randpic_iphone6;
        private int special_type;
        private String ipad_desc;
        private String is_publish;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMo_type() {
            return mo_type;
        }

        public void setMo_type(int mo_type) {
            this.mo_type = mo_type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRandpic() {
            return randpic;
        }

        public void setRandpic(String randpic) {
            this.randpic = randpic;
        }

        public String getRandpic_ios5() {
            return randpic_ios5;
        }

        public void setRandpic_ios5(String randpic_ios5) {
            this.randpic_ios5 = randpic_ios5;
        }

        public String getRandpic_desc() {
            return randpic_desc;
        }

        public void setRandpic_desc(String randpic_desc) {
            this.randpic_desc = randpic_desc;
        }

        public String getRandpic_ipad() {
            return randpic_ipad;
        }

        public void setRandpic_ipad(String randpic_ipad) {
            this.randpic_ipad = randpic_ipad;
        }

        public String getRandpic_qq() {
            return randpic_qq;
        }

        public void setRandpic_qq(String randpic_qq) {
            this.randpic_qq = randpic_qq;
        }

        public String getRandpic_2() {
            return randpic_2;
        }

        public void setRandpic_2(String randpic_2) {
            this.randpic_2 = randpic_2;
        }

        public String getRandpic_iphone6() {
            return randpic_iphone6;
        }

        public void setRandpic_iphone6(String randpic_iphone6) {
            this.randpic_iphone6 = randpic_iphone6;
        }

        public int getSpecial_type() {
            return special_type;
        }

        public void setSpecial_type(int special_type) {
            this.special_type = special_type;
        }

        public String getIpad_desc() {
            return ipad_desc;
        }

        public void setIpad_desc(String ipad_desc) {
            this.ipad_desc = ipad_desc;
        }

        public String getIs_publish() {
            return is_publish;
        }

        public void setIs_publish(String is_publish) {
            this.is_publish = is_publish;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("PicBean{");
            sb.append("type=").append(type);
            sb.append(", mo_type=").append(mo_type);
            sb.append(", code='").append(code).append('\'');
            sb.append(", randpic='").append(randpic).append('\'');
            sb.append(", randpic_ios5='").append(randpic_ios5).append('\'');
            sb.append(", randpic_desc='").append(randpic_desc).append('\'');
            sb.append(", randpic_ipad='").append(randpic_ipad).append('\'');
            sb.append(", randpic_qq='").append(randpic_qq).append('\'');
            sb.append(", randpic_2='").append(randpic_2).append('\'');
            sb.append(", randpic_iphone6='").append(randpic_iphone6).append('\'');
            sb.append(", special_type=").append(special_type);
            sb.append(", ipad_desc='").append(ipad_desc).append('\'');
            sb.append(", is_publish='").append(is_publish).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
