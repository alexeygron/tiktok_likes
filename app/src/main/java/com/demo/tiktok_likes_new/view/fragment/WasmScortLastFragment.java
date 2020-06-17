package com.demo.tiktok_likes_new.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.net.NetConfigure;
import com.demo.tiktok_likes_new.net.request.WasmScortUserVideoResp;
import com.demo.tiktok_likes_new.net.request.WasmScortUserVideosRequest;
import com.demo.tiktok_likes_new.view.activity.WasmScortMakeActivityWasmScort;
import com.demo.tiktok_likes_new.view.custom.WasmScortImage;

import java.util.concurrent.Executors;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.demo.tiktok_likes_new.util.Common.TOK_REQUEST_ENABLED;
import static com.demo.tiktok_likes_new.util.Common.runOnMainThread;

public class WasmScortLastFragment extends WasmScortBaseFragment {

    private String TAG = WasmScortLastFragment.class.getSimpleName();

    private RecyclerView wasm_mPhotosList;
    private ListPostsAdapter wasm_mListPostsAdapter;
    private ProgressBar wasm_mProgressBar;
    private String wasm_cursor = "0";
    public static WasmScortUserVideoResp wasm_wasmScortUserVideoResponse;

    public static WasmScortLastFragment newInstance() {
        WasmScortLastFragment f = new WasmScortLastFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wasm_mProgressBar = view.findViewById(R.id.progressBar2);
        wasm_mPhotosList = view.findViewById(R.id.list_data);

        //GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        StaggeredGridLayoutManager layoutManager =  new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        wasm_mPhotosList.setLayoutManager(layoutManager);

        VideosDataSource dataSource = new VideosDataSource();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(3)
                .build();

        PagedList<WasmScortUserVideoResp.Item> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(ContextCompat.getMainExecutor(getActivity()))
                .build();


        wasm_mListPostsAdapter = new ListPostsAdapter(new DiffUtil.ItemCallback<WasmScortUserVideoResp.Item>() {


            @Override
            public boolean areItemsTheSame(@NonNull WasmScortUserVideoResp.Item oldItem, @NonNull WasmScortUserVideoResp.Item newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull WasmScortUserVideoResp.Item oldItem, @NonNull WasmScortUserVideoResp.Item newItem) {
                return false;
            }
        });
        wasm_mListPostsAdapter.submitList(pagedList);
        wasm_mPhotosList.setAdapter(wasm_mListPostsAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wasm_scort_one_fragment, container, false);
    }

    public class ListPostsAdapter extends PagedListAdapter<WasmScortUserVideoResp.Item, ListPostsAdapter.ViewHolder> {


        protected ListPostsAdapter(DiffUtil.ItemCallback<WasmScortUserVideoResp.Item> diffUtilCallback) {
            super(diffUtilCallback);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wasm_scort_photo_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Context context = holder.cover.getContext();
            WasmScortUserVideoResp.Item item = getItem(position);
            Transformation<Bitmap> circleCrop = new CenterCrop();
            if (item != null) {
                holder.likeSize.setText(String.valueOf(item.getLikesCount()));
                Glide.with(context)
                        .load(item.getPhotoAnim())
                        .optionalTransform(circleCrop)
                        .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
                        .transition(withCrossFade())
                        .into(holder.cover);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            WasmScortImage cover;
            TextView likeSize;

            ViewHolder(View itemView) {
                super(itemView);
                cover = itemView.findViewById(R.id.video_preview);
                likeSize = itemView.findViewById(R.id.status);
                itemView.setOnClickListener(v -> WasmScortMakeActivityWasmScort.start(NetConfigure.CONTEXT, getItem(getAdapterPosition()), getBalance()));
            }
        }

    }

    class VideosDataSource extends PositionalDataSource<WasmScortUserVideoResp.Item> {

        boolean sd= false;

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<WasmScortUserVideoResp.Item> callback) {
            //Log.d(TAG, "loadInitial, requestedStartPosition = " + paramsMap.requestedStartPosition + ", requestedLoadSize = " + paramsMap.requestedLoadSize);
            if (TOK_REQUEST_ENABLED) {
                runOnMainThread(() -> new WasmScortUserVideosRequest().loadUserVideos(callback, null, wasm_cursor).observe(getActivity(), userVideoResp -> {
                    wasm_wasmScortUserVideoResponse = userVideoResp;
                    wasm_mProgressBar.setVisibility(View.GONE);
                }));
            }
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<WasmScortUserVideoResp.Item> callback) {
            //Log.d(TAG, "loadRange, startPosition = " + paramsMap.startPosition + ", loadSize = " + paramsMap.loadSize);
            if (wasm_wasmScortUserVideoResponse != null && TOK_REQUEST_ENABLED && wasm_wasmScortUserVideoResponse.isMore()) {
                runOnMainThread(() -> new WasmScortUserVideosRequest().loadUserVideos(null, callback, wasm_wasmScortUserVideoResponse.getMaxCursor()).observe(getActivity(), userVideoResp -> wasm_wasmScortUserVideoResponse = userVideoResp));
            }
        }
    }
}
