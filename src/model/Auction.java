package model;

public class Auction
{
    public Auction(){
        this.price=0;
        this.isOVer=false;
    }
    public Auction(int price,Boolean isOVer){
        this.price=price;
        this.isOVer=isOVer;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getOVer() {
        return isOVer;
    }

    public void setOVer(Boolean OVer) {
        isOVer = OVer;
    }

    private int price;
    private Boolean isOVer;

}
