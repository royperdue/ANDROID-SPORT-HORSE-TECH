package com.sporthorsetech.horseshoepad.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.sporthorsetech.horseshoepad.R;
import com.sporthorsetech.horseshoepad.backend.horseApi.HorseApi;
import com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse;

import java.io.IOException;

public class PostDataAsync extends AsyncTask<Void, Void, Horse>
{
    private Activity context;
    private HorseApi api;
    private GoogleAccountCredential credential;
    private Horse horse;
    private ProgressDialog progressDialog;

    public PostDataAsync(Activity context, GoogleAccountCredential credential, Horse horse)
    {
        this.context = context;
        this.credential = credential;
        this.horse = horse;
    }

    private void init()
    {
        if (api == null)
        {
            HorseApi.Builder builder = new HorseApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), credential);
                    //.setRootUrl(context.getString(R.string.project_id));
            api = builder.build();
        }
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.show();
        this.progressDialog.setMessage(context.getString(R.string.wait));
    }

    @Override
    protected Horse doInBackground(Void... params)
    {
        try
        {
            init();
            return api.horse().save(horse).execute();
        } catch (IOException e)
        {
            Log.e("UNEXPECTED ERROR", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Horse result)
    {
        this.progressDialog.dismiss();
        if (result != null)
        {
            Toast.makeText(context, context.getString(R.string.new_horse_success), Toast.LENGTH_LONG).show();
        } else
        {
            Toast.makeText(context, context.getString(R.string.new_horse_error), Toast.LENGTH_LONG).show();
        }
    }
}