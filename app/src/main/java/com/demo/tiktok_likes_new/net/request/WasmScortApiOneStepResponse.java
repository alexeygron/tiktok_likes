package com.demo.tiktok_likes_new.net.request;

public class WasmScortApiOneStepResponse {

    private String passw = "";
    private String balance_lfs = "";
    private String balance_fsf = "";
    private String regards_lfs = "";
    private String regards_fsf = "";
    private String user_bonuce = "";
    private String auth_type = "";
    private String udp_url = "";
    private String field = "";
    private int udp_type;
    private String udp_text = "";

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getBalance_lfs() {
        return balance_lfs;
    }

    public void setBalance_lfs(String balance_lfs) {
        this.balance_lfs = balance_lfs;
    }

    public String getBalance_fsf() {
        return balance_fsf;
    }

    public void setBalance_fsf(String balance_fsf) {
        this.balance_fsf = balance_fsf;
    }

    public String getRegards_lfs() {
        return regards_lfs;
    }

    public void setRegards_lfs(String regards_lfs) {
        this.regards_lfs = regards_lfs;
    }

    public String getRegards_fsf() {
        return regards_fsf;
    }

    public void setRegards_fsf(String regards_fsf) {
        this.regards_fsf = regards_fsf;
    }

    public String getUser_bonuce() {
        return user_bonuce;
    }

    public void setUser_bonuce(String user_bonuce) {
        this.user_bonuce = user_bonuce;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public String getUdp_url() {
        return udp_url;
    }

    public String getField() {
        return field;
    }

    public void setField(String wasm_field) {
        field = wasm_field;
    }

    public void setUdp_url(String udp_url) {
        this.udp_url = udp_url;
    }

    public int getUdp_type() {
        return udp_type;
    }

    public void setUdp_type(int udp_type) {
        this.udp_type = udp_type;
    }

    public String getUdp_text() {
        return udp_text;
    }

    public void setUdp_text(String udp_text) {
        this.udp_text = udp_text;
    }

    @Override
    public String toString() {
        return "WasmScortApiOneStepResponse{" +
                "passw='" + passw + '\'' +
                ", balance_lfs='" + balance_lfs + '\'' +
                ", balance_fsf='" + balance_fsf + '\'' +
                ", regards_lfs='" + regards_lfs + '\'' +
                ", regards_fsf='" + regards_fsf + '\'' +
                ", user_bonuce='" + user_bonuce + '\'' +
                ", auth_type='" + auth_type + '\'' +
                ", udp_url='" + udp_url + '\'' +
                ", udp_type='" + udp_type + '\'' +
                ", udp_text='" + udp_text + '\'' +
                '}';
    }
}
