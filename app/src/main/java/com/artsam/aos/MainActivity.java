package com.artsam.aos;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.artsam.aos.adapter.MyRecAdapter;
import com.artsam.aos.entity.Sample;
import com.artsam.aos.listener.MyChildEventListener;
import com.artsam.aos.service.AccelerometerService;
import com.artsam.aos.view.MyPlotView;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {

    public static final String MAIN_TAG = "my_app";
    private static final String STATE_PLOT = "statePlot";
    public static final int FIRST_SAMPLE_POS = 0;
    public static final int TIME_INTERVAL = 3000;

    public static Firebase mFireBaseRef;

    private LinearLayout mLayoutMain;
    private Context mContext = this;
    private boolean mIsBound;
    private List<Sample> mSamples = new ArrayList<>();
    private ServiceConnection mConnection = new ServiceConnection() {
        private AccelerometerService mAccBoundService;
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mAccBoundService = ((AccelerometerService.AccelerometerBinder)service).getService();

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

        mLayoutMain = (LinearLayout) findViewById(R.id.layout_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.switch_control);
        switchCompat.setOnCheckedChangeListener(this);

        Firebase.setAndroidContext(this);
        mFireBaseRef = new Firebase("https://accobserverservice.firebaseio.com/measurements");

        RecyclerView recView = new RecyclerView(this);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(new MyRecAdapter(mSamples));
        recView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, 3));
        mLayoutMain.addView(recView);


        MyPlotView plotView = new MyPlotView(this);
        plotView.setSamples(mSamples);
        plotView.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 0, 2));
        mLayoutMain.addView(plotView);

        mFireBaseRef.addChildEventListener(new MyChildEventListener(recView, plotView));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(MAIN_TAG, "MainActivity: onCheckedChanged " + isChecked);
        if (isChecked){
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
