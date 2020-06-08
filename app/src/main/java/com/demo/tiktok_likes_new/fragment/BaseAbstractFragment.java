package com.demo.tiktok_likes_new.fragment;

import androidx.fragment.app.Fragment;

import com.demo.tiktok_likes_new.activity.MainActivity;

public abstract class BaseAbstractFragment extends Fragment {


    void setBalance(String value) {
        try {
            if (getActivity() != null && getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).setUpBalance(value);
        } catch (Exception ignored) {

        }
    }
}
