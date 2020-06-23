package com.demo.tiktok_likes_new.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.demo.tiktok_likes_new.R;
import com.demo.tiktok_likes_new.WaspApp;
import com.demo.tiktok_likes_new.net.NetConfigure;
import com.demo.tiktok_likes_new.net.parser.WasmScortApiGetVideoParser;
import com.demo.tiktok_likes_new.net.request.WasmScortApiBuyBlsRequest;
import com.demo.tiktok_likes_new.net.request.WasmScortApiGetVideoResponse;
import com.demo.tiktok_likes_new.view.custom.WasmScortText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Callback;

import static com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED;
import static com.android.billingclient.api.BillingClient.BillingResponseCode.OK;
import static com.demo.tiktok_likes_new.net.NetConfigure.getWasmScortUtilsCr;

public class WasmScortFourFragment extends WasmScortBaseFragment  implements View.OnClickListener{

    private String TAG = WasmScortFourFragment.class.getSimpleName();

    private Lot[] wasm_lotList = initLots();
    private HashMap<String, SkuDetails> wasm_hashMap = new HashMap<>();
    private List<String> wasm_skuList = new ArrayList<>();

    private WasmScortText marker1, marker2, marker3, marker4, marker5;
    private View View1, View2, View3, View4, View5;

    private BillingClient wasm_billingClient;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        marker1 = view.findViewById(R.id.marker1);
        marker1.setOnClickListener(this);
        marker2 = view.findViewById(R.id.marker2);
        marker2.setOnClickListener(this);
        marker3 = view.findViewById(R.id.marker3);
        marker3.setOnClickListener(this);
        marker4 = view.findViewById(R.id.marker4);
        marker4.setOnClickListener(this);
        marker5 = view.findViewById(R.id.marker5);
        marker5 .setOnClickListener(this);
        View1 = view.findViewById(R.id.View1);
        View1.setOnClickListener(this);
        View2 = view.findViewById(R.id.View2);
        View2.setOnClickListener(this);
        View3 = view.findViewById(R.id.View3);
        View3.setOnClickListener(this);
        View4 = view.findViewById(R.id.View4);
        View4.setOnClickListener(this);
        View5 = view.findViewById(R.id.View5);
        View5.setOnClickListener(this);
        setUpBillingClient();
        updatePrices();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wasm_scort_four_fragment, container, false);
    }

    private List<PurchaseHistoryRecord> purchaseList = new ArrayList<>();

    private void setUpBillingClient() {
        wasm_billingClient = BillingClient.newBuilder(NetConfigure.CONTEXT).enablePendingPurchases().setListener(purchasesUpdatedListener).build();
        wasm_billingClient.startConnection(new BillingClientStateListener() {

            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == OK) {
                    new Handler().post(QuerySkuDetails::new);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });

        wasm_skuList.clear();

        for (Lot lot : wasm_lotList) {
            wasm_skuList.add(lot.getId());
        }
    }

    private Lot[] initLots() {
        return new Lot[]{
               new Lot("scort.wasmapp1", "100"),
               new Lot("scort.wasmapp2", "1000"),
               new Lot("scort.wasmapp3", "3000"),
               new Lot("scort.wasmapp4", "8000"),
               new Lot("scort.wasmapp5", "25000"),
        };
    }

    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {

        @Override
        public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
            if (OK == billingResult.getResponseCode()) {
                if (purchases != null && purchases.size() > 0) {
                    wasm_billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, purchaseHistoryResponseListener);
                }
            }
        }
    };

    private void startApiOneBuyRequest(String token, String id) {
        new WasmScortApiBuyBlsRequest( token,  id).start(new Callback() {

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) {
                try {
                    String resp = response.body().string();
                    WasmScortApiGetVideoResponse wasmScortApiGetVideoResponse = new WasmScortApiGetVideoParser().parse(getWasmScortUtilsCr().ShaiDesc(resp));
                    WaspApp.wasm_storage.setBfgl(Float.parseFloat(wasmScortApiGetVideoResponse.getBalance()));
                    setBalance(wasmScortApiGetVideoResponse.getBalance());

                    consume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }
        });
    }


    private PurchasesListener purchasesListener = new PurchasesListener() {

        @Override
        public void onSkuDetailsResponse() {
            updatePrices();
        }

        @Override
        public void onPurchaseHistoryAsync(List<PurchaseHistoryRecord> purchases) {
            for (PurchaseHistoryRecord purchase : purchases) {
                new Handler().post(() -> startApiOneBuyRequest(purchase.getPurchaseToken(), purchase.getSku()));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.marker1 | R.id.View1:
                launchBillingFlow(getActivity(), wasm_lotList[0].getId());
                break;
            case R.id.marker2 | R.id.View2:
                launchBillingFlow(getActivity(), wasm_lotList[1].getId());
                break;
            case R.id.marker3 | R.id.View3:
                launchBillingFlow(getActivity(), wasm_lotList[2].getId());
                break;
            case R.id.marker4 | R.id.View4:
               launchBillingFlow(getActivity(), wasm_lotList[3].getId());
                break;
            case R.id.marker5 | R.id.View5:
                launchBillingFlow(getActivity(), wasm_lotList[4].getId());
                break;
        }
    }

    private void launchBillingFlow(Activity activity, String sku) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSkuDetails(wasm_hashMap.get(sku)).build();
        int responseCode = wasm_billingClient.launchBillingFlow(activity, flowParams).getResponseCode();
        if (responseCode == ITEM_ALREADY_OWNED) {
            wasm_billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, purchaseHistoryResponseListener);
        }
    }

    private void consume() {
        new ConsumePurchase();
    }

    private void updatePrices() {
        try {
            if (wasm_hashMap != null && wasm_hashMap.size() > 3) {
                marker1.setText(wasm_hashMap.get(wasm_lotList[0].getId()).getPrice());
                marker2.setText(wasm_hashMap.get(wasm_lotList[1].getId()).getPrice());
                marker3.setText(wasm_hashMap.get(wasm_lotList[2].getId()).getPrice());
                marker4.setText(wasm_hashMap.get(wasm_lotList[3].getId()).getPrice());
                marker5.setText(wasm_hashMap.get(wasm_lotList[4].getId()).getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WasmScortFourFragment newInstance() {
        WasmScortFourFragment f = new WasmScortFourFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    public interface PurchasesListener {
        void onSkuDetailsResponse();

        void onPurchaseHistoryAsync(List<PurchaseHistoryRecord> purchases);
    }

    public class Lot {
        String id, balance;

        Lot(String wasm_id, String wasm_balance) {
            id = wasm_id;
            balance = wasm_balance;
        }

        public String getId() {
            return id;
        }

        public void setId(String wasm_id) {
            id = wasm_id;
        }

        public String getBalance() {
            return balance;
        }

    }

    private PurchaseHistoryResponseListener purchaseHistoryResponseListener = (billingResult, purchasesList) -> {
        if (billingResult.getResponseCode() == OK && purchasesList.size() > 0) {
            purchaseList.addAll(purchasesList);
            if (purchasesListener != null) purchasesListener.onPurchaseHistoryAsync(purchasesList);
            new ConsumePurchase();
        }
    };

    class QuerySkuDetails implements Runnable, SkuDetailsResponseListener {

        @Override
        public void run() {
            wasm_billingClient.querySkuDetailsAsync(
                    SkuDetailsParams
                            .newBuilder()
                            .setSkusList(wasm_skuList)
                            .setType(BillingClient.SkuType.INAPP).build(),
                    this);
        }

        @Override
        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
            if (OK == billingResult.getResponseCode()) {
                for (SkuDetails skuDetails : skuDetailsList) {
                    wasm_hashMap.put(skuDetails.getSku(), skuDetails);
                }
                if (purchasesListener != null) purchasesListener.onSkuDetailsResponse();
                wasm_billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, purchaseHistoryResponseListener);
            }
        }
    }

    class ConsumePurchase implements ConsumeResponseListener {

        ConsumePurchase() {
            PurchaseHistoryRecord purchase = purchaseList.get(0);
            ConsumeParams params = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .setDeveloperPayload(purchase.getDeveloperPayload())
                    .build();
            wasm_billingClient.consumeAsync(params, this);
        }

        @Override
        public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
            if (billingResult.getResponseCode() == OK) {
                purchaseList.remove(0);
            }
        }
    }
}
