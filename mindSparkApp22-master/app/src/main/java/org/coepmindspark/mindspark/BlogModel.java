package org.coepmindspark.mindspark;

public class BlogModel {
    String title;
    String img;
    String url;
    String des;

    public BlogModel(String title, String img, String url, String des) {
        this.title = title;
        this.img = img;
        this.url = url;
        this.des = des;
    }

    public BlogModel(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
