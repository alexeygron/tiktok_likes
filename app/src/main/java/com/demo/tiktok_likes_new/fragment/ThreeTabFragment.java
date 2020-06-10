package com.demo.tiktok_likes_new.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.network.parser.ApiGetVideoParser;
import com.demo.tiktok_likes_new.network.request.ApiAccertRequest;
import com.demo.tiktok_likes_new.network.request.ApiGetHistoryRequest;
import com.demo.tiktok_likes_new.network.request.ApiGetVideoResponse;

import java.io.IOException;

import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.network.Constants.getAbaBUtilsCrypt;

public class ThreeTabFragment extends BaseAbstractFragment {

    private RecyclerView mPhotosList;
    private ListDataAdapter listDataAdapter;

    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.three_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = view.findViewById(R.id.progressBar2);
        mPhotosList = view.findViewById(R.id.list_data);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mPhotosList.setLayoutManager(layoutManager);

        listDataAdapter = new ListDataAdapter();
        mPhotosList.setAdapter(listDataAdapter);

        startLoadDataRequest();
    }

    private void startLoadDataRequest() {
        new ApiGetHistoryRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    /*ApiOneStepResponse apiOneStepResponse = new StartAppParser().parse(getAbaBUtilsCrypt().AbaBDecryptString(resp));
                    App.initDataStorage.setApiOneStepResponse(apiOneStepResponse);*/
                    Log.i(TAG, "resp " + getAbaBUtilsCrypt().AbaBDecryptString(resp));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }

    public static ThreeTabFragment newInstance() {
        ThreeTabFragment f = new ThreeTabFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }



        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView ivPhoto;

            ViewHolder(View itemView) {
                super(itemView);
               // ivPhoto = itemView.findViewById(R.id.iv_photo);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.container:
                        //onClick();
                        break;
                }
            }
        }
    }
}
