package org.nrna.dto.response.news;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
    private int current;
    private String next;

    @JsonProperty("other_pages")
    private OtherPages otherPages;

    @JsonGetter("current")
    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @JsonGetter("next")
    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public OtherPages getOtherPages() {
        return otherPages;
    }

    public void setOtherPages(OtherPages otherPages) {
        this.otherPages = otherPages;
    }
}
