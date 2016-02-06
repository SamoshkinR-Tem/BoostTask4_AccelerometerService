package com.artsam.aos.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.artsam.aos.MainActivity;
import com.firebase.client.Firebase;

public class AccelerometerService extends Service
        implements SensorEventListener {

    private long lastUpdate = 0;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Firebase mFirebaseRef;
    private int mCounter = 0;
    private Firebase mSampleRef;

    private class Measurement {
        private float x;
        private float y;
        private float z;

        public Measurement() {
        }

        public Measurement(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class AccelerometerBinder extends Binder {
        public AccelerometerService getService() {
            Log.d(MainActivity.MAIN_TAG, "AccelerometerBinder: getService");
            return AccelerometerService.this;
        }
    }

    // This is the object that receives interactions from clients.
    // See RemoteService for a more complete example.
    private final IBinder mBinder = new AccelerometerBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(MainActivity.MAIN_TAG, "onStartCommand: Received start id " + startId + ": " + intent);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(MainActivity.MAIN_TAG, "AccelerometerService: onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.d(MainActivity.MAIN_TAG, "AccelerometerService: onCreate");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        mFirebaseRef = new Firebase("https://accobserverservice.firebaseio.com/");
    }

    @Override
    public void onDestroy() {
        Log.d(MainActivity.MAIN_TAG, "AccelerometerService: onDestroy");
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - lastUpdate) > 1000) {
                Log.d(MainActivity.MAIN_TAG, "AccelerometerService: onSensorChanged " +
                        String.valueOf(event.values[0]));

                mSampleRef = mFirebaseRef.child("measurements").child("sample " + mCounter);
                Measurement m = new Measurement(event.values[0], event.values[1], event.values[2]);
                mSampleRef.setValue(m);

                mCounter++;
                lastUpdate = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}