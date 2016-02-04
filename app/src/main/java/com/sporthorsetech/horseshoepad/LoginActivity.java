package com.sporthorsetech.horseshoepad;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.sporthorsetech.horseshoepad.async.GetUserTask;
import com.sporthorsetech.horseshoepad.model.MyApplication;
import com.sporthorsetech.horseshoepad.utility.Constant;

public class LoginActivity extends Activity
{
    private GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button button = (Button) this.findViewById(R.id.loginId);

        credential = GoogleAccountCredential.usingAudience(this, Constant.AUTH_SCOPE);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseAccount();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        String accountName = null;
        switch (requestCode)
        {
            case Constant.REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null)
                {
                    accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null)
                    {
                        saveCredentials(accountName);
                        // With the account name acquired, go get the auth token
                        getUsername(accountName);
                    }
                }
                break;
            case Constant.REQUEST_CODE_RECOVER_FROM_AUTH_ERROR:
                if (resultCode == RESULT_OK)
                {
                    // Receiving a result that follows a GoogleAuthException, try auth again
                    getUsername(accountName);
                }
                break;
            case Constant.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR:
                if (resultCode == RESULT_OK)
                {
                    // Receiving a result that follows a GoogleAuthException, try auth again
                    getUsername(accountName);
                }
                break;
        }
    }

    private void saveCredentials(String accountName)
    {
        credential.setSelectedAccountName(accountName);
        ((MyApplication) this.getApplication()).setGoogleAccountCredential(credential);
    }

    private void chooseAccount()
    {
        startActivityForResult(credential.newChooseAccountIntent(),
                Constant.REQUEST_ACCOUNT_PICKER);
    }

    private void getUsername(String accountName)
    {
        if (accountName == null)
        {
            chooseAccount();
        } else
        {
            if (isDeviceOnline())
            {
                new GetUserTask(this, accountName, Constant.SCOPE, credential).execute();
            } else
            {
                Toast.makeText(this, "Error! Make sure that you are connected to the Internet!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isDeviceOnline()
    {
        ConnectivityManager conMgr = (ConnectivityManager) this.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable())
        {
            return false;
        }
        return true;
    }

    public void handleException(final Exception e)
    {
        // Because this call comes from the AsyncTask, we must ensure that the following
        // code instead executes on the UI thread.
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (e instanceof GooglePlayServicesAvailabilityException)
                {
                    // The Google Play services APK is old, disabled, or not present.
                    // Show a dialog created by Google Play services that allows
                    // the user to update the APK
                    int statusCode = ((GooglePlayServicesAvailabilityException) e)
                            .getConnectionStatusCode();
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
                            LoginActivity.this,
                            Constant.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                    dialog.show();
                } else if (e instanceof UserRecoverableAuthException)
                {
                    // Unable to authenticate, such as when the user has not yet granted
                    // the app access to the account, but the user can fix this.
                    // Forward the user to an activity in Google Play services.
                    Intent intent = ((UserRecoverableAuthException) e).getIntent();
                    startActivityForResult(intent,
                            Constant.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
                }
            }
        });
    }
}
