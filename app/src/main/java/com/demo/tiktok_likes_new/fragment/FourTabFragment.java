package com.demo.tiktok_likes_new.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.tiktok_likes_new.R;

public class FourTabFragment extends BaseAbstractFragment {

    private String TAG = FourTabFragment.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.four_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static FourTabFragment newInstance() {
        FourTabFragment f = new FourTabFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }


}
