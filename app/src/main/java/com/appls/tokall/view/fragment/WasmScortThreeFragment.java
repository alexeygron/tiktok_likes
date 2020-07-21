package com.appls.tokall.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.appls.tokall.R;
import com.appls.tokall.net.parser.WasmScortApiGetHistoryParser;
import com.appls.tokall.net.request.WasmScortApiGetHistoryRequest;
import com.appls.tokall.net.request.WasmScortApiGetHistoryResponse;
import com.appls.tokall.net.request.WasmScortApiRemoveHistoryRequest;
import com.appls.tokall.view.custom.WasmScortImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.appls.tokall.net.NetConfigure.getWasmScortUtilsCr;
import static com.appls.tokall.util.WasmScortUtilsCr.getStrObj;

public class WasmScortThreeFragment extends WasmScortBaseFragment {

    private RecyclerView wasm_mPhotosList;
    private ListDataAdapter wasm_listDataAdapter;
    private List<WasmScortApiGetHistoryResponse.Item> wasm_historyItems = new ArrayList<>();

    private ProgressBar wasm_mProgressBar;

    public static WasmScortThreeFragment newInstance() {
        WasmScortThreeFragment f = new WasmScortThreeFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wasm_mProgressBar = view.findViewById(R.id.progressBar2);
        wasm_mPhotosList = view.findViewById(R.id.list_data);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        wasm_mPhotosList.setLayoutManager(layoutManager);

        wasm_listDataAdapter = new ListDataAdapter();
        wasm_mPhotosList.setAdapter(wasm_listDataAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wasm_scort_three_fragment, container, false);
    }

    private void startLoadDataRequest() {
        new WasmScortApiGetHistoryRequest().start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    WasmScortApiGetHistoryResponse wasmScortApiGetHistoryResponse = new WasmScortApiGetHistoryParser().parse(getWasmScortUtilsCr().ShaiDesc(resp));
                    onRequestComplete(wasmScortApiGetHistoryResponse);
                    //Log.i(TAG, "resp " + wasmScortApiGetHistoryResponse.getItemList().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }

    private void startRemoveItemRequest(WasmScortApiGetHistoryResponse.Item item) {
        new WasmScortApiRemoveHistoryRequest(item).start(new Callback() {

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
                wasm_mProgressBar.setVisibility(View.GONE);
            }
        });

        wasm_mProgressBar.setVisibility(View.VISIBLE);
    }

    private void onRequestComplete(WasmScortApiGetHistoryResponse response) {
        if (response != null && getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                wasm_historyItems = response.getItemList();
                wasm_listDataAdapter.notifyDataSetChanged();
                wasm_mProgressBar.setVisibility(View.GONE);
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startLoadDataRequest();
    }

    class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {

        Handler progressBarHandler = new Handler();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wasm_scort_make_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WasmScortApiGetHistoryResponse.Item item = wasm_historyItems.get(position);

            Glide.with(getActivity())
                    .load(item.getPhoto_url())
                    .transition(withCrossFade())
                    .into(holder.photo);

            holder.progressBar.setProgress((int) (100 * (((float) item.getScsf()) / item.getLsbl())));
            holder.state.setText(item.getScsf() + " " + getStrObj(R.string.dgd_res_awq) + " " + item.getLsbl());
        }

        @Override
        public int getItemCount() {
            return wasm_historyItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ProgressBar progressBar;
            WasmScortImage photo;
            WasmScortImage closeIcon;
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
                            .setMessage(getStrObj(R.string.dgd_res_cfbbnn))
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> startRemoveItemRequest(wasm_historyItems.get(getAdapterPosition())))
                            .setNegativeButton(android.R.string.cancel,null)
                            .show();
                });
            }
        }
    }

}
