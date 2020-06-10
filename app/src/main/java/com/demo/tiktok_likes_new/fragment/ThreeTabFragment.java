package com.demo.tiktok_likes_new.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.network.parser.ApiGetHistoryParser;
import com.demo.tiktok_likes_new.network.request.ApiGetHistoryRequest;
import com.demo.tiktok_likes_new.network.request.ApiGetHistoryResponse;
import com.demo.tiktok_likes_new.network.request.ApiRemoveHistoryRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.network.Constants.getAbaBUtilsCrypt;

public class ThreeTabFragment extends BaseAbstractFragment {

    private RecyclerView mPhotosList;
    private ListDataAdapter listDataAdapter;
    private List<ApiGetHistoryResponse.Item> historyItems = new ArrayList<>();

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
                    ApiGetHistoryResponse apiGetHistoryResponse = new ApiGetHistoryParser().parse(getAbaBUtilsCrypt().AbaBDecryptString(resp));
                    onRequestComplete(apiGetHistoryResponse);
                    Log.i(TAG, "resp " + apiGetHistoryResponse.getItemList().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }

    private void startRemoveItemRequest(ApiGetHistoryResponse.Item item) {
        new ApiRemoveHistoryRequest(item).start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {

                    if (response.isSuccessful()) {
                        startLoadDataRequest();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                mProgressBar.setVisibility(View.GONE);
            }
        });

        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void onRequestComplete(ApiGetHistoryResponse response) {
        if (response != null && getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                historyItems = response.getItemList();
                listDataAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            });
        }
    }

    public static ThreeTabFragment newInstance() {
        ThreeTabFragment f = new ThreeTabFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {

        Handler progressBarHandler = new Handler();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ApiGetHistoryResponse.Item item = historyItems.get(position);

            Glide.with(getActivity())
                    .load(item.getPhoto_url())
                    .transition(withCrossFade())
                    .into(holder.photo);

            holder.progressBar.setProgress((int) (100 * (((float) item.getScsf()) / item.getLsbl())));
            holder.state.setText(String.format(getString(R.string.msg6), item.getScsf(), item.getLsbl()));
        }

        @Override
        public int getItemCount() {
            return historyItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ProgressBar progressBar;
            ImageView photo;
            ImageView closeIcon;
            TextView state;

            ViewHolder(View itemView) {
                super(itemView);
                photo = itemView.findViewById(R.id.photo);
                closeIcon = itemView.findViewById(R.id.close_icon);
                state = itemView.findViewById(R.id.state);
                progressBar = itemView.findViewById(R.id.progressBar4);
                closeIcon.setOnClickListener(v -> {
                    new AlertDialog
                            .Builder(getActivity())
                            .setMessage(R.string.msg7)
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> startRemoveItemRequest(historyItems.get(getAdapterPosition())))
                            .setNegativeButton(android.R.string.cancel,null)
                            .show();
                });
            }
        }
    }
}
