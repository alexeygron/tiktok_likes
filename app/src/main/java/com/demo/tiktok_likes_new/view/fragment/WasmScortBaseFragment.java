package com.demo.tiktok_likes_new.view.fragment;

import androidx.fragment.app.Fragment;

import com.demo.tiktok_likes_new.view.activity.WasmScortNavActivityWasmScort;

public abstract class WasmScortBaseFragment extends Fragment {


    String getBalance() {
        try {
            if (getActivity() != null && getActivity() instanceof WasmScortNavActivityWasmScort)
                return ((WasmScortNavActivityWasmScort) getActivity()).getBlns();
        } catch (Exception ignored) {

        }
        return "";
    }

    void setBalance(String value) {
        try {
            if (getActivity() != null && getActivity() instanceof WasmScortNavActivityWasmScort)
                ((WasmScortNavActivityWasmScort) getActivity()).setUpBarState(value);
        } catch (Exception ignored) {

        }
    }
}
