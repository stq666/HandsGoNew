package com.soyomaker.handsgo.ui;

import org.ligi.gobandroidhd.ai.gnugo.IGnuGoService;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;

import com.soyomaker.handsgo.R;
import com.soyomaker.handsgo.util.AppConstants;

public class FightActivity extends BaseActivity {

    private static final String TAG = "FightActivity";

    private IGnuGoService mGnuGoService;
    private ServiceConnection mGnugoConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        initView();

        mGnugoConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mGnuGoService = IGnuGoService.Stub.asInterface(service);
            }
        };
        getApplication().bindService(new Intent(AppConstants.INTENT_GNUGO_ACTION),
                mGnugoConnection, Context.BIND_AUTO_CREATE);
    }

    public void onDestory() {
        super.onDestroy();
        if (mGnuGoService != null) {
            try {
                getApplication().unbindService(mGnugoConnection);
                getApplication().stopService(new Intent(AppConstants.INTENT_GNUGO_ACTION));
            } catch (Exception e) {
            }
        }
    }

    private void initView() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_fight);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public String getPageName() {
        return "对弈界面";
    }
}