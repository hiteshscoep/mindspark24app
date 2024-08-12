package org.coepmindspark.mindspark;

public class Events {
    private String title;
    private String description;
    private String thumbnail;
    private String coverImg;
    private long timestamp;
    private double x_cord;
    private double y_cord;
    private String eventLink;
    private String module;
    private int isPopular;
    private String venue;
    private String winners;
    private String fees;
    private String price;


    public Events() {
    }

    private String coverPhoto;

    public Events(String title, String thumbnail, String coverPhoto) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;
    }

    public Events(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Events(String title, String description, String thumbnail, String coverImg, long timestamp, double x_cord, double y_cord, String eventLink, String module, int isPopular, String venue, String winners, String fees, String price, String coverPhoto) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.coverImg = coverImg;
        this.timestamp = timestamp;
        this.x_cord = x_cord;
        this.y_cord = y_cord;
        this.eventLink = eventLink;
        this.module = module;
        this.isPopular = isPopular;
        this.venue = venue;
        this.winners = winners;
        this.fees = fees;
        this.price = price;
        this.coverPhoto = coverPhoto;
    }

    public int getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(int isPopular) {
        this.isPopular = isPopular;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getX_cord() {
        return x_cord;
    }

    public void setX_cord(double x_cord) {
        this.x_cord = x_cord;
    }

    public double getY_cord() {
        return y_cord;
    }

    public void setY_cord(double y_cord) {
        this.y_cord = y_cord;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getWinners() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winners = winners;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
