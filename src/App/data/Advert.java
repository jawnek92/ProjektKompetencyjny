package App.data;

import javafx.scene.image.Image;

public class Advert {
    private String title;
    private String price;
    private String linkToAdvertisment;
    private String imageLink;
    private Image image;

    public Advert(String title, String price, String linkToAdvertisment, String linkToImage) {
        this.title = title;
        this.price = price;
        this.linkToAdvertisment = linkToAdvertisment;
        this.image = new Image(linkToImage);
        this.imageLink = linkToImage;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getLinkToAdvertisment() {
        return linkToAdvertisment;
    }

    public Image getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLinkToAdvertisment(String linkToAdvertisment) {
        this.linkToAdvertisment = linkToAdvertisment;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
