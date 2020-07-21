package com.appls.tokall.net.request;

public class WasmScortApiGetVideoResponse {

    private String balance = "";
    private String order_id = "";
    private String item_id = "";
    private String item_image = "";
    private int item_type = 0;
    private String item_hash = "";
    private boolean isOrderAvailable = false;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getItem_hash() {
        return item_hash;
    }

    public void setItem_hash(String item_hash) {
        this.item_hash = item_hash;
    }

    public boolean isOrderAvailable() {
        return isOrderAvailable;
    }

    public void setOrderAvailable(boolean orderAvailable) {
        isOrderAvailable = orderAvailable;
    }

    @Override
    public String toString() {
        return "WasmScortApiGetVideoResponse{" +
                "balance='" + balance + '\'' +
                ", order_id='" + order_id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", item_image='" + item_image + '\'' +
                ", item_type=" + item_type +
                ", item_hash='" + item_hash + '\'' +
                ", isOrderAvailable=" + isOrderAvailable +
                '}';
    }
}
