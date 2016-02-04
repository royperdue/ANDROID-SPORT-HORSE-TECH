package com.sporthorsetech.horseshoepad.model;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class MyApplication extends android.app.Application
{
    private GoogleAccountCredential googleAccountCredential;
    private User user;

    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
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
