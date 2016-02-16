package com.artsam.aos;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.artsam.aos.adapter.TabPagerAdapter;
import com.artsam.aos.service.AccelerometerService;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {

    public static final String MAIN_TAG = "my_app";
    public static final int FIRST_SAMPLE_POS = 0;
    public static final int TIME_INTERVAL = 1000;

    public static Firebase mFireBaseRef;

    private Context mContext = this;
    private boolean mIsBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        private AccelerometerService mAccBoundService;

        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mAccBoundService = ((AccelerometerService.AccelerometerBinder) service).getService();

            // Tell the user about this.
            Toast.makeText(mContext, R.string.local_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mAccBoundService = null;

            // Tell the user about this.
            Toast.makeText(mContext, R.string.local_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.data)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.users)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_my_tabs);
        final TabPagerAdapter tabAdapter = new TabPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Firebase.setAndroidContext(this);
        mFireBaseRef = new Firebase("https://accobserverservice.firebaseio.com/measurements");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SwitchCompat switchCompat = (SwitchCompat) menu.getItem(0)
                .getActionView().findViewById(R.id.switch_control);
        switchCompat.setOnCheckedChangeListener(this);

        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(MAIN_TAG, "MainActivity: onCheckedChanged " + isChecked);
        if (isChecked) {
            doBindService();
        } else {
            doUnbindService();
        }
    }

    void doBindService() {
        Log.d(MAIN_TAG, "MainActivity: doBindService");
        // Establish a connection with the service.  We use an explicit
        // class name because we want a specific service implementation that
        // we know will be running in our own process (and thus won't be
        // supporting component replacement by other applications).
        bindService(new Intent(this, AccelerometerService.class),
                mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        Log.d(MAIN_TAG, "MainActivity: doUnbindService");
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(MAIN_TAG, "MainActivity: onDestroy");
        super.onDestroy();
        doUnbindService();
    }
}
