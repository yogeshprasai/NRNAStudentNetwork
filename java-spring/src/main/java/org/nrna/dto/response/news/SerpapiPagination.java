package org.nrna.dto.response.news;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SerpapiPagination {

    @JsonProperty("current")
    private int current;

    @JsonProperty("next_link")
    private String nextLink;

    @JsonProperty("next")
    private String next;

    @JsonProperty("other_pages")
    private OtherPages otherPages;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

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
