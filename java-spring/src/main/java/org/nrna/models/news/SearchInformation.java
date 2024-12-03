package org.nrna.models.news;

import com.fasterxml.jackson.annotation.JsonGetter;

public class SearchInformation {
    private String queryDisplayed;
    private int totalResults;
    private double timeTakenDisplayed;
    private String newsResultsState;

    @JsonGetter("query_displayed")
    public String getQueryDisplayed() {
        return queryDisplayed;
    }

    public void setQueryDisplayed(String queryDisplayed) {
        this.queryDisplayed = queryDisplayed;
    }

    @JsonGetter("total_results")
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @JsonGetter("time_taken_displayed")
    public double getTimeTakenDisplayed() {
        return timeTakenDisplayed;
    }

    public void setTimeTakenDisplayed(double timeTakenDisplayed) {
        this.timeTakenDisplayed = timeTakenDisplayed;
    }

    @JsonGetter("news_results_state")
    public String getNewsResultsState() {
        return newsResultsState;
    }

    public void setNewsResultsState(String newsResultsState) {
        this.newsResultsState = newsResultsState;
    }
}
