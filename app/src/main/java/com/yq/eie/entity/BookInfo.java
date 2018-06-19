package com.yq.eie.entity;

import java.io.Serializable;
import java.util.List;

public class BookInfo implements Serializable {

    private static final long serialVersionUID = -2630567815286719099L;

    private int count;
    private int start;
    private int total;
    private List<BookBean> books;

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookInfo{");
        sb.append("count=").append(count);
        sb.append(", start=").append(start);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
