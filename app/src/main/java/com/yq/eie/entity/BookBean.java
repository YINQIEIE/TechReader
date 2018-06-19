package com.yq.eie.entity;

import java.io.Serializable;
import java.util.List;

public class BookBean implements Serializable {

    /**
     * rating : {"max":10,"numRaters":65110,"average":"9.0","min":0}
     * subtitle :
     * author : ["三毛"]
     * pubdate : 1976
     * tags : [{"count":22276,"name":"三毛","title":"三毛"},{"count":10674,"name":"撒哈拉的故事","title":"撒哈拉的故事"},{"count":6833,"name":"旅行","title":"旅行"},{"count":5960,"name":"散文","title":"散文"},{"count":4632,"name":"随笔","title":"随笔"},{"count":2840,"name":"文学","title":"文学"},{"count":2771,"name":"台湾","title":"台湾"},{"count":1730,"name":"小说","title":"小说"}]
     * origin_title :
     * image : https://img3.doubanio.com/view/subject/m/public/s3563113.jpg
     * binding : 平裝
     * translator : []
     * catalog : 媽媽的一封信《代序》
     * 回鄉小箋《四版代序》
     * 沙漠中的飯店
     * 結婚記
     * 懸壺濟世
     * 娃娃新娘
     * 荒山之夜
     * 沙漠觀浴記
     * 愛的尋求
     * 芳鄰
     * 素人漁夫
     * 死果
     * 天梯
     * 白手成家
     * pages : 240
     * images : {"small":"https://img3.doubanio.com/view/subject/s/public/s3563113.jpg","large":"https://img3.doubanio.com/view/subject/l/public/s3563113.jpg","medium":"https://img3.doubanio.com/view/subject/m/public/s3563113.jpg"}
     * alt : https://book.douban.com/subject/1361264/
     * id : 1361264
     * publisher : 皇冠出版社
     * isbn10 : 9573305542
     * isbn13 : 9789573305545
     * title : 撒哈拉的故事
     * url : https://api.douban.com/v2/book/1361264
     * alt_title :
     * author_intro : 台灣的著名作家──三毛
     * 1943年3月26日出生於重慶，浙江省定海縣人。本名為陳懋平，1946年改名陳平。
     * 筆名『三毛』更不知出自何處，只在她的《鬧學記》一篇序文中提及『三毛』二字暗藏一個《易經》的卦象，至於這卦象究竟隱藏何種玄機，序文中並沒有進一步的交代。
     * 三毛的母親繆進蘭曾在〈我的女兒，大家的三毛〉這篇文章中，提及四個兄弟姊妹裡，次女三毛的性格最為特行卓立、不依常規，以及不能忍受虛假，因此父母要在她身邊看守著每一腳步是否踏穩。
     * 另外，有讀者認為『流浪』才是三毛真正的名字，無論是她的遺作、她的遊歷和她心靈情感的轉折，始終透著一點點浪跡天涯的意味。
     * 1973年7月，三毛與荷西在沙漠小鎮阿尤恩結婚。與荷西一道生活的歲月，三毛的文章充滿歡笑、喜樂，讀者閱讀他的小說，彷彿感受著她愉悅的婚姻生活，即使面對大風沙的侵襲，她仍顯得積極與樂觀。然而自荷西離開人世後，三毛的文章也頓時『黑暗』了起來，其文字不再有笑容，代替的只是無盡的悲傷。
     * 1990年，三毛創作的電影劇本《滾滾紅塵》榮獲金馬獎八項大獎。其生平重要著作有《撒哈拉的故事》、《雨季不再來》、《哭泣的駱駝》、《我的寶貝》、《鬧學記》……等散文小說集，譯有《娃娃看天下》、《蘭嶼之歌》，以及創作電影劇本《滾滾紅塵》、《親愛的三毛》、《我的快樂天堂》、《高原的百合花》。
     * 然而令人惋惜的是，1991年1月4日凌晨，三毛在醫院自縊身亡，享年四十八歲。
     * summary : 三毛作品中最膾炙人口的《撒哈拉的故事》﹐
     * 由１２篇精采動人的散文結合而成﹐
     * 其中＜沙漠中的飯店＞﹐
     * 是三毛適應荒涼單調的沙漠生活後﹐
     * 重新拾筆的第一篇文字﹐
     * 自此之後﹐三毛便寫出一系列以沙漠為背景的故事﹐
     * 風靡了全世界的中文讀者。
     * series : {"id":"352","title":"三毛全集"}
     * price : 160 TWD
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private SeriesBean series;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;
    private List<?> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<?> getTranslator() {
        return translator;
    }

    public void setTranslator(List<?> translator) {
        this.translator = translator;
    }

    public static class RatingBean implements Serializable {

        private static final long serialVersionUID = 6511836793399987877L;
        /**
         * max : 10
         * numRaters : 65110
         * average : 9.0
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean implements Serializable {

        private static final long serialVersionUID = -8400612298282242520L;
        /**
         * small : https://img3.doubanio.com/view/subject/s/public/s3563113.jpg
         * large : https://img3.doubanio.com/view/subject/l/public/s3563113.jpg
         * medium : https://img3.doubanio.com/view/subject/m/public/s3563113.jpg
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

    public static class SeriesBean implements Serializable {

        private static final long serialVersionUID = -9080237358589591421L;
        /**
         * id : 352
         * title : 三毛全集
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TagsBean implements Serializable {

        private static final long serialVersionUID = -1378013643362772375L;
        /**
         * count : 22276
         * name : 三毛
         * title : 三毛
         */

        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
