package org.coepmindspark.mindspark;

public class TeamModel {
    private String name;
    private String position;
    private String img;
    private String mail;
    private String linked_in;
    private String insta;

    public TeamModel(String name, String position, String img, String mail, String linked_in, String insta) {
        this.name = name;
        this.position = position;
        this.img = img;
        this.mail = mail;
        this.linked_in = linked_in;
        this.insta = insta;
    }
    public TeamModel(){

    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLinked_in() {
        return linked_in;
    }

    public void setLinked_in(String linked_in) {
        this.linked_in = linked_in;
    }
}
