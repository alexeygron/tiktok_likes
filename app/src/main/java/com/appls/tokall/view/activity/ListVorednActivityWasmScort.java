package com.appls.tokall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import com.appls.tokall.R;
import com.appls.tokall.TokkallApp;
import com.appls.tokall.net.NetConfigure;
import com.appls.tokall.util.StarDialog;
import com.appls.tokall.util.WasmScortUpdDialog;
import com.appls.tokall.view.custom.WasmScortText;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.hawk.Hawk;

import static com.appls.tokall.util.Common.cookies_tag;
import static com.appls.tokall.util.WasmScortUtilsCr.getStrObj;

public class ListVorednActivityWasmScort extends WasmScortBaseActivity {

    private BottomNavigationViewEx mBottomNavigation;
    private WasmScortText mTextBalanceStatus;
    private String blns = "0";
    DrawerLayout drawer;
    private AppInitListener appInitListener = () -> {
        if (TokkallApp.wasm_storage.getApiOneStepResponse() != null)
            runOnUiThread(() -> {
                setUpBarState(TokkallApp.wasm_storage.getApiOneStepResponse().getBalance_lfs());


                if (!TextUtils.isEmpty(TokkallApp.wasm_storage.getApiOneStepResponse().getUdp_url())) {
                    WasmScortUpdDialog.show(this);
                }

                findViewById(R.id.loading_screen).setVisibility(View.GONE);
            });
    };

    public static void start(final Context context) {
        final Intent intent = new Intent(context, ListVorednActivityWasmScort.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wasm_scort_activity_navigation);

        }


    public void onRats(View view) {
        StarDialog.show(this);
        drawer.closeDrawers();
    }

    public String getBlns() {
        return blns;
    }

    public void setUpBarState(@NonNull String stat) {
        if (mTextBalanceStatus != null && !stat.isEmpty()) {
            blns = stat;
            mTextBalanceStatus.setText(getStrObj(R.string.dgd_res_vbtgh) + " " + stat);
            mTextBalanceStatus.setVisibility(View.VISIBLE);
        }
    }

    public void onExit(View view) {
        new AlertDialog.Builder(this).setMessage(getStrObj(R.string.dgd_res_exit_quest)).setPositiveButton(android.R.string.ok, (dialog, which) -> {
            Hawk.delete(cookies_tag);
            AppAuthActivityWasmScort.start(NetConfigure.CONTEXT);
            finish();
        }).setNegativeButton(android.R.string.cancel, null).show();
        drawer.closeDrawers();
    }

    public interface AppInitListener{
        void onAppInit();
    }



}
