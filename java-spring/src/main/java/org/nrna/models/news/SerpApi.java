package org.nrna.models.news;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;

public class SerpApi {

    private SearchMetadata searchMetadata;
    private SearchParameters searchParameters;
    private SearchInformation searchInformation;
    private ArrayList<NewsResult> newsResults;
    private Pagination pagination;
    private SerpapiPagination serpapiPagination;

    public SearchMetadata getSearchMetadata() {
        return searchMetadata;
    }

    @JsonSetter("search_metadata")
    public void setSearchMetadata(SearchMetadata searchMetadata) {
        this.searchMetadata = searchMetadata;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    @JsonSetter("search_parameters")
    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public SearchInformation getSearchInformation() {
        return searchInformation;
    }

    @JsonSetter("search_information")
    public void setSearchInformation(SearchInformation searchInformation) {
        this.searchInformation = searchInformation;
    }

    @JsonGetter("newsResults")
    public ArrayList<NewsResult> getNewsResults() {
        return newsResults;
    }

    @JsonSetter("news_results")
    public void setNewsResults(ArrayList<NewsResult> newsResults) {
        this.newsResults = newsResults;
    }

    public Pagination getPagination() {
        return pagination;
    }

    @JsonSetter("pagination")
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public SerpapiPagination getSerpapiPagination() {
        return serpapiPagination;
    }

    @JsonSetter("serpapi_pagination")
    public void setSerpapiPagination(SerpapiPagination serpapiPagination) {
        this.serpapiPagination = serpapiPagination;
    }
}
