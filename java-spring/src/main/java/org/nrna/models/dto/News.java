package org.nrna.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String newsDate;

    private String persistDate;

    @Column(length=1000)
    private String link;

    @Column(length=1000)
    private String title;

    @Column(length=1000)
    private String source;

    @Column(length=1000)
    private String snippet;

    @Column(length=1000)
    private String thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = parseSerpApiDate(newsDate);
    }

    public String getPersistDate() {
        return persistDate;
    }

    public void setPersistDate(String persistDate) {
        this.persistDate = persistDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    private String parseSerpApiDate(String newsDateString){
        String date = null;
        if(newsDateString != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            try {
                if(newsDateString.contains("day ago") || newsDateString.contains("days ago")){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(newsDateString.replaceAll("[^0-9]", "")));
                    date = formatter.format(c.getTimeInMillis());
                }else if(newsDateString.contains("week ago") || newsDateString.contains("weeks ago")){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.WEEK_OF_MONTH, -Integer.parseInt(newsDateString.replaceAll("[^0-9]", "")));
                    date = formatter.format(c.getTimeInMillis());
                }else if(newsDateString.contains("month ago") || newsDateString.contains("months ago")){
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(newsDateString.replaceAll("[^0-9]", ""))* 30);
                    date = formatter.format(c.getTimeInMillis());
                }
            } catch (Exception e) {
                date = new Date().toString();
                throw new RuntimeException(e);
            }
        }
        if(date == null) {
            return newsDateString;
        }else{
            return date;
        }
    }
}
