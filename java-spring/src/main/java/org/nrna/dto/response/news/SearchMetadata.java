package org.nrna.dto.response.news;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchMetadata {
    private String id;
    private String status;
    private String jsonEndpoint;
    private String createdAt;
    private String processedAt;
    private String googleUrl;
    private String rawHtmlFile;
    private double totalTimeTaken;

    @JsonGetter("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonGetter("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonGetter("json_endpoint")
    public String getJsonEndpoint() {
        return jsonEndpoint;
    }

    public void setJsonEndpoint(String jsonEndpoint) {
        this.jsonEndpoint = jsonEndpoint;
    }

    @JsonGetter("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonGetter("processed_at")
    public String getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(String processedAt) {
        this.processedAt = processedAt;
    }

    @JsonGetter("google_url")
    public String getGoogleUrl() {
        return googleUrl;
    }

    public void setGoogleUrl(String googleUrl) {
        this.googleUrl = googleUrl;
    }

    @JsonGetter("raw_html_file")
    public String getRawHtmlFile() {
        return rawHtmlFile;
    }

    public void setRawHtmlFile(String rawHtmlFile) {
        this.rawHtmlFile = rawHtmlFile;
    }

    @JsonGetter("total_time_taken")
    public double getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(double totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }
}
