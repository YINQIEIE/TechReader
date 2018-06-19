package com.yq.eie.entity;

/**
 * 歌手信息类
 */
public class Artist extends SortBase {

    private int id;//ID
    private String name;//歌手名字
    private int count;//专辑数量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Artist{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", count=").append(count);
        sb.append(", firstChar=").append(firstChar);
        sb.append('}');
        return sb.toString();
    }
}
