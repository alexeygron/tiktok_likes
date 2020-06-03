package com.demo.tiktok_likes_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.tiktok_likes_new.network.UserVideosRequest;
import com.demo.tiktok_likes_new.network.data.UserVideoResp;

import java.util.concurrent.Executors;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class UserPhotosFragment extends Fragment {

    private String TAG = UserPhotosFragment.class.getSimpleName();

    private RecyclerView mPhotosList;
    private ListPostsAgapter mListPostsAdapter;
    private String cursor = "0";
    private boolean hashMore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_photos_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotosList = view.findViewById(R.id.photos_list);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mPhotosList.setLayoutManager(layoutManager);

        VideosDataSource dataSource = new VideosDataSource();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(3)
                .build();

        PagedList<UserVideoResp.Item> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(ContextCompat.getMainExecutor(getActivity()))
                .build();


        mListPostsAdapter = new ListPostsAgapter(new DiffUtil.ItemCallback<UserVideoResp.Item>() {


            @Override
            public boolean areItemsTheSame(@NonNull UserVideoResp.Item oldItem, @NonNull UserVideoResp.Item newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull UserVideoResp.Item oldItem, @NonNull UserVideoResp.Item newItem) {
                return false;
            }
        });
        mListPostsAdapter.submitList(pagedList);
        mPhotosList.setAdapter(mListPostsAdapter);
    }

    static UserPhotosFragment newInstance() {
        UserPhotosFragment f = new UserPhotosFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    public class ListPostsAgapter extends PagedListAdapter<UserVideoResp.Item, ListPostsAgapter.ViewHolder> {


        protected ListPostsAgapter(DiffUtil.ItemCallback<UserVideoResp.Item> diffUtilCallback) {
            super(diffUtilCallback);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Context context = holder.cover.getContext();
            UserVideoResp.Item item = getItem(position);
            if (item != null) {
                holder.likeSize.setText(String.valueOf(item.getLikesCount()));
                Glide.with(context)
                        .load(item.getPhoto())
                        .transition(withCrossFade())
                        .into(holder.cover);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView cover;
            TextView likeSize;

            ViewHolder(View itemView) {
                super(itemView);
                cover = itemView.findViewById(R.id.iv_photo);
                likeSize = itemView.findViewById(R.id.tv_digg_count);
                itemView.setOnClickListener(v -> startOrderScreen());
            }
        }

    }

    private void startOrderScreen() {
        startActivity(new Intent(getActivity(), OrderActivity.class));
    }

    class VideosDataSource extends PositionalDataSource<UserVideoResp.Item> {

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<UserVideoResp.Item> callback) {
            //Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize);
            new UserVideosRequest().loadUserVideos(callback, null, cursor);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<UserVideoResp.Item> callback) {
            //Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);

            //new UserVideosRequest().loadUserVideos(null, callback, cursor);
        }
    }
}
