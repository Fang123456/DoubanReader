package com.k.douban.domain;

/**
 * Created by AAAAA on 2015/12/15.
 */
public class SearchBook {


    private String id;
    private String name;    //title
    private String description;    //
    private String summary;     //summary
    private String iconpath;    //link(2)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getIconpath() {
        return iconpath;
    }
    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }
}
