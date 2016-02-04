package com.sporthorsetech.horseshoepad.model;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class MyApplication extends android.app.Application
{
    private GoogleAccountCredential googleAccountCredential;
    private User user;

    @Override
    public void onCreate()
    {
        MultiDex.install(this);
        super.onCreate();
    }

    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public GoogleAccountCredential getGoogleAccountCredential()
    {
        return googleAccountCredential;
    }

    public void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential)
    {
        this.googleAccountCredential = googleAccountCredential;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
}
