package org.nrna.models.news;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleAlsoSearchFor {
    private String name;
    private List<NewsResult> newsResults;

    @JsonIgnore
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<NewsResult> getNewsResults() {
        return newsResults;
    }

    public void setNewsResults(List<NewsResult> newsResults) {
        this.newsResults = newsResults;
    }
}
