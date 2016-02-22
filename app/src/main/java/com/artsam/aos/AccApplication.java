package com.artsam.aos;

import com.firebase.client.Firebase;

/**
 * @author Artem Samoshkin
 * @since 20/02/16
 *
 * Initialize Firebase with the application context. This must happen before the client is used.
 */
public class AccApplication extends android.app.Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
