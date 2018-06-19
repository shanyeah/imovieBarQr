package cn.com.imovie.imoviebar.bean;

/**
 * Created by glin on 1/26/16.
 */
public class MoviePrice {

    private String roomCategoryId;
    private String roomCategoryName;
    private float price;

    public String getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(String roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }

    public String getRoomCategoryName() {
        return roomCategoryName;
    }

    public void setRoomCategoryName(String roomCategoryName) {
        this.roomCategoryName = roomCategoryName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MoviePrice() {
    }

    public MoviePrice(String roomCategoryId, String roomCategoryName, float price) {
        this.roomCategoryId = roomCategoryId;
        this.roomCategoryName = roomCategoryName;
        this.price = price;
    }
}
