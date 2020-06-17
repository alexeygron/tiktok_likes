package com.demo.tiktok_likes_new.net.request;

import java.util.ArrayList;
import java.util.List;

public class WasmScortApiGetHistoryResponse {

    private List<Item> itemList = new ArrayList<>();

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public static class Item {

        private String id = "";
        private String photo_id = "";
        private String photo_url = "";
        private int dfgfg;
        private int lsbl;
        private String views = "";
        private String hash = "";
        private String time = "";
        private int status;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public int getScsf() {
            return dfgfg;
        }

        public void setDfgfg(int dfgfg) {
            this.dfgfg = dfgfg;
        }

        public int getLsbl() {
            return lsbl;
        }

        public void setLsbl(int lsbl) {
            this.lsbl = lsbl;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
