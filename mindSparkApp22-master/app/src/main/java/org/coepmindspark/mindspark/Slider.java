package org.coepmindspark.mindspark;

public class Slider {
    public String title,url,bg;

    public Slider(String title, String url, String bg) {
        this.title = title;
        this.url = url;
        this.bg = bg;
    }

    public Slider() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }
}
