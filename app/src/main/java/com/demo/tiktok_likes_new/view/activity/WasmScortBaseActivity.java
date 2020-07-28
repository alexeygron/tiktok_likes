package com.demo.tiktok_likes_new.view.activity;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


public class WasmScortBaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
