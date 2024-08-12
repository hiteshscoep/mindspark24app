package org.coepmindspark.mindspark;

public class SponsorsModel {
    String title;
    String img;

    public SponsorsModel(String title, String img) {
        this.title = title;
        this.img = img;
    }
    public SponsorsModel(){

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
