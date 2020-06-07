package com.demo.tiktok_likes_new.network.data;

import java.util.ArrayList;
import java.util.List;

public class UserVideoResp {

    private List<Item> items = new ArrayList<>();
    private String maxCursor = "";
    private String minCursor = "";
    private boolean isMore;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getMaxCursor() {
        return maxCursor;
    }

    public void setMaxCursor(String maxCursor) {
        this.maxCursor = maxCursor;
    }

    public String getMinCursor() {
        return minCursor;
    }

    public void setMinCursor(String minCursor) {
        this.minCursor = minCursor;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        this.isMore = more;
    }

    public static class Item {

        private String id = "";
        private String uniqueId = "";
        private String likesCount = "";
        private String photo = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLikesCount() {
            return likesCount;
        }

        public void setLikesCount(String likesCount) {
            this.likesCount = likesCount;
        }

        public String getPhoto() {
            return photo;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", uniqueId='" + uniqueId + '\'' +
                    ", likesCount='" + likesCount + '\'' +
                    ", photo='" + photo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserVideoResp{" +
                "items=" + items +
                '}';
    }
}
