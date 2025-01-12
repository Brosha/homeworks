package org.example;

import java.util.Objects;

public class URLEntity {
    private String URL;
    private String shortCut;
    private int limit;
    private int clicks;
    private long lifetime;
    private String userID;


    public URLEntity(String URL, String shortCut, int limit, long lifetime, String userID) {
        this.URL = URL;
        this.shortCut = shortCut;
        this.limit = limit;
        this.clicks = 0;
        this.lifetime = lifetime;
        this.userID = userID;
    }

    public URLEntity(String URL, String shortCut, String userID) {
        this.URL = URL;
        this.shortCut = shortCut;
        this.userID = userID;
    }

    public String getURL() {
        return URL;
    }

    public String getShortCut() {
        return shortCut;
    }

    public int getLimit() {
        return limit;
    }

    public int getClicks() {
        return clicks;
    }

    public long getLifetime() {
        return lifetime;
    }

    public String getUserID() {
        return userID;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLifetime(long lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public String toString() {
        return "URLEntity{" +
                "URL='" + URL + '\'' +
                ", shortCut='" + shortCut + '\'' +
                ", limit=" + limit +
                ", clicks=" + clicks +
                ", lifetime=" + lifetime +
                ", userID='" + userID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URLEntity urlEntity = (URLEntity) o;
        return limit == urlEntity.limit && clicks == urlEntity.clicks && lifetime == urlEntity.lifetime && Objects.equals(URL, urlEntity.URL) && Objects.equals(shortCut, urlEntity.shortCut) && Objects.equals(userID, urlEntity.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(URL, shortCut, limit, clicks, lifetime, userID);
    }
}
