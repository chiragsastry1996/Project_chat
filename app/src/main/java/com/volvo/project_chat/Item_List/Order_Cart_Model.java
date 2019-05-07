package com.volvo.project_chat.Item_List;


public class Order_Cart_Model{
    private String id;
    private String name;
    private String quantity;
    private String image;
    private String unit_price;
    private String key;
    private String total_amount;

    public Order_Cart_Model(String id, String name, String quantity, String image, String unit_price, String key) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.unit_price = unit_price;
        this.key = key;

    }


    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}