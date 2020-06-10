package com.demo.tiktok_likes_new.network.data;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static class Item implements Parcelable {

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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.uniqueId);
            dest.writeString(this.likesCount);
            dest.writeString(this.photo);
        }

        public Item() {
        }

        protected Item(Parcel in) {
            this.id = in.readString();
            this.uniqueId = in.readString();
            this.likesCount = in.readString();
            this.photo = in.readString();
        }

        public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
    }

    @Override
    public String toString() {
        return "UserVideoResp{" +
                "items=" + items +
                '}';
    }
}
