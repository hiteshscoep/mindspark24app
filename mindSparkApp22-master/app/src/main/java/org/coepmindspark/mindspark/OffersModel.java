package org.coepmindspark.mindspark;

public class OffersModel{
    private String title;
    private String des;
    private String img;
    private String url;
    public OffersModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OffersModel(String title, String des, String img, String url) {
        this.title = title;
        this.des = des;
        this.img = img;
        this.url = url;
    }

}
