package org.nrna.dto.response.news;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class SearchParameters {

    private String engine;
    private String q;
    private String locationRequested;
    private String locationUsed;
    private String googleDomain;
    private String hl;
    private String gl;
    private String device;
    private String tbm;
    private String num;

    @JsonGetter("engine")
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @JsonSetter("q")
    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @JsonGetter("location_requested")
    public String getLocationRequested() {
        return locationRequested;
    }

    public void setLocationRequested(String locationRequested) {
        this.locationRequested = locationRequested;
    }

    @JsonGetter("location_used")
    public String getLocationUsed() {
        return locationUsed;
    }

    public void setLocationUsed(String locationUsed) {
        this.locationUsed = locationUsed;
    }

    @JsonGetter("google_domain")
    public String getGoogleDomain() {
        return googleDomain;
    }

    public void setGoogleDomain(String googleDomain) {
        this.googleDomain = googleDomain;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTbm() {
        return tbm;
    }

    public void setTbm(String tbm) {
        this.tbm = tbm;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
