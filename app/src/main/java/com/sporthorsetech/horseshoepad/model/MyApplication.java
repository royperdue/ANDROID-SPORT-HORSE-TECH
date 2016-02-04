package com.sporthorsetech.horseshoepad.model;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

/**
 * Created by eltonjhony on 14/01/16.
 */
public class MyApplication extends android.app.Application {

    private GoogleAccountCredential googleAccountCredential;
    private User user;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public GoogleAccountCredential getGoogleAccountCredential() {
        return googleAccountCredential;
    }

    public void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential) {
        this.googleAccountCredential = googleAccountCredential;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
