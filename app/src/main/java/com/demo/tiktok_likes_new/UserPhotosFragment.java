package com.demo.tiktok_likes_new;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

    private String TAG = "UserPhotosFragment";

    private RecyclerView photosList;
    private ListPostsAgapter listPostsAgapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_photos_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photosList = view.findViewById(R.id.photos_list);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        photosList.setLayoutManager(layoutManager);

        MyPositionalDataSource dataSource = new MyPositionalDataSource();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(3)
                .build();

        PagedList<UserVideoResp.Item> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(ContextCompat.getMainExecutor(getActivity()))
                .build();


        listPostsAgapter = new ListPostsAgapter(new DiffUtil.ItemCallback<UserVideoResp.Item>() {


            @Override
            public boolean areItemsTheSame(@NonNull UserVideoResp.Item oldItem, @NonNull UserVideoResp.Item newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull UserVideoResp.Item oldItem, @NonNull UserVideoResp.Item newItem) {
                return false;
            }
        });
        listPostsAgapter.submitList(pagedList);
        photosList.setAdapter(listPostsAgapter);

        /*GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        photosList.setLayoutManager(layoutManager);
        listPostsAgapter = new ListPostsAgapter();
        //listPostsAgapter.setOnClickListener(clickListener);
        photosList.setAdapter(listPostsAgapter);*/

    }


    public static class ListPostsAgapter extends PagedListAdapter<UserVideoResp.Item, ListPostsAgapter.ViewHolder> {


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
            Context context = holder.ivPhoto.getContext();
            UserVideoResp.Item item = getItem(position);
            if (item != null) {
                holder.tvDiggCount.setText(String.valueOf(item.getLikesCount()));
                Glide.with(context)
                        .load(item.getPhoto())
                        .transition(withCrossFade())
                        .into(holder.ivPhoto);
            }
        }

        /*private ClickListener clickListener;
        public void setOnClickListener(ClickListener raceClickListener) {
            this.clickListener = raceClickListener;
        }*/

       /* void setData(List<UserVideo> data) {
            //this.data = data;
            //notifyDataSetChanged();
            for (UserVideo item : data) {
                this.data.add(item);
                notifyItemInserted(this.data.size() - 1);
            }
        }

        public void clear() {
            data.clear();
            notifyDataSetChanged();
        }*/



        /*@NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Context context = holder.ivPhoto.getContext();
            UserVideo item = data.get(position);
            holder.tvDiggCount.setText(String.valueOf(item.getStats().getDiggCount()));
            Glide.with(context)
                    .load(item.getVideo().getCover())
                    .transition(withCrossFade())
                    .into(holder.ivPhoto);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }*/

       /* public interface ClickListener {
            void onClick(AwemeListItem item);
        }*/

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView ivPhoto;
            TextView tvDiggCount;
            ViewGroup container;

            ViewHolder(View itemView) {
                super(itemView);
                ivPhoto = itemView.findViewById(R.id.iv_photo);
                tvDiggCount = itemView.findViewById(R.id.tv_digg_count);
                container = itemView.findViewById(R.id.container);
                container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.container:
                        //onClick();
                        break;
                }
            }

            /*public void onClick() {
                if (clickListener != null) {
                    clickListener.onClick(data.get(getAdapterPosition()));
                }
            }*/
        }

    }

    class MyPositionalDataSource extends PositionalDataSource<UserVideoResp.Item> {

        // private final EmployeeStorage employeeStorage;

        /*public MyPositionalDataSource(EmployeeStorage employeeStorage) {
            this.employeeStorage = employeeStorage;
        }*/

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<UserVideoResp.Item> callback) {
            Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize);
            new UserVideosRequest().loadUserVideos(callback);
            /*List<UserVideo> result = employeeStorage.getData(params.requestedStartPosition, params.requestedLoadSize);
            callback.onResult(result, 0);*/
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<UserVideoResp.Item> callback) {
            Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
            /*List<UserVideo> result = employeeStorage.getData(params.startPosition, params.loadSize);
            callback.onResult(result);*/
        }
    }
}
