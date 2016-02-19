package com.sporthorsetech.horseshoepad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.LittleDB;
import com.sporthorsetech.horseshoepad.utility.equine.BatteryReading;
import com.sporthorsetech.horseshoepad.utility.equine.Gait;
import com.sporthorsetech.horseshoepad.utility.equine.GaitActivity;
import com.sporthorsetech.horseshoepad.utility.equine.HorseHoof;
import com.sporthorsetech.horseshoepad.utility.equine.Step;
import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.www.horseApi.HorseApi;
import com.sporthorsetech.www.horseApi.model.AccelerationX;
import com.sporthorsetech.www.horseApi.model.AccelerationY;
import com.sporthorsetech.www.horseApi.model.AccelerationZ;
import com.sporthorsetech.www.horseApi.model.Account;
import com.sporthorsetech.www.horseApi.model.Force;
import com.sporthorsetech.www.horseApi.model.Horse;
import com.sporthorsetech.www.horseApi.model.JsonMap;
import com.sporthorsetech.www.horseApi.model.Owner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private HorseApi horseApi = null;
    private GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]
    }

    @Override
    public void onStart()
    {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone())
        {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else
        {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>()
            {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult)
                {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result)
    {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess())
        {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            LittleDB.getInstance(getApplicationContext()).putString(Constant.HORSE_OWNER_EMAIL, acct.getEmail());
            LittleDB.getInstance(getApplicationContext()).putString(Constant.HORSE_OWNER_NAME, acct.getDisplayName());

            updateUI(true);
        } else
        {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>()
                {
                    @Override
                    public void onResult(Status status)
                    {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess()
    {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>()
                {
                    @Override
                    public void onResult(Status status)
                    {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog()
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn)
    {
        if (signedIn)
        {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);

            new PostDataAsync().execute();

            finish();
        } else
        {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }

    class PostDataAsync extends AsyncTask<Void, Void, Void>
    {
        private List<com.sporthorsetech.horseshoepad.utility.equine.Horse> horseList;

        protected void onPreExecute()
        {
            horseList = Database.with(getApplicationContext())
                    .load(com.sporthorsetech.horseshoepad.utility.equine.Horse.TYPE.horse).orderByTs(Database.SORT_ORDER.ASC).limit(Constant.MAX_HORSES).execute();
        }

        protected Void doInBackground(Void... arg0)
        {
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectionManager.getActiveNetworkInfo();
            credential = GoogleAccountCredential.usingAudience(getApplicationContext(), "server:client_id:" + Constant.WEB_CLIENT_ID);
            credential.setSelectedAccountName(LittleDB.getInstance(getApplicationContext()).getString(Constant.HORSE_OWNER_EMAIL));

            HorseApi.Builder builder = new HorseApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), credential)
                    .setRootUrl("https://sporthorsetech.appspot.com/_ah/api/");
            builder.setApplicationName(getApplicationContext().getString(R.string.app_name));
            horseApi = builder.build();

            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            {
                JsonMap horses = new JsonMap();

                List<com.sporthorsetech.www.horseApi.model.HorseHoof> horseHooves = new ArrayList<>();
                List<com.sporthorsetech.www.horseApi.model.GaitActivity> gaitActivities = new ArrayList<>();
                List<com.sporthorsetech.www.horseApi.model.Gait> gaits = new ArrayList<>();
                List<com.sporthorsetech.www.horseApi.model.BatteryReading> batteryReadings = new ArrayList<>();
                List<com.sporthorsetech.www.horseApi.model.Step> steps = new ArrayList<>();

                for (com.sporthorsetech.horseshoepad.utility.equine.Horse h : horseList)
                {
                    Horse horse = new Horse();
                    horse.setId(Long.parseLong(h.getStoredObjectId()));
                    horse.setName(h.getName());
                    horse.setTimeCreated(h.getTimeCreated());
                    horse.setHeight(h.getHeight());
                    horse.setAge(h.getAge());
                    horse.setBreed(h.getBreed());
                    horse.setSex(h.getSex());
                    horse.setDiscipline(h.getDiscipline());

                    if (h.getHorseHooves().size() > 0)
                    {
                        for (HorseHoof hoof : h.getHorseHooves())
                        {
                            com.sporthorsetech.www.horseApi.model.HorseHoof horseHoof = new com.sporthorsetech.www.horseApi.model.HorseHoof();
                            horseHoof.setId(Long.parseLong(hoof.getStoredObjectId()));
                            horseHoof.setTimeCreated(hoof.getTimeCreated());
                            horseHoof.setFoot(hoof.getFoot());
                            horseHoof.setCurrentHorseShoePad(hoof.getCurrentHorseShoePad());
                            horseHoof.setPreviousHorseshoePads(hoof.getPreviousHorseshoePads());
                            horseHooves.add(horseHoof);
                        }
                    }

                    if (h.getGaitActivities().size() > 0)
                    {
                        for (GaitActivity g : h.getGaitActivities())
                        {
                            com.sporthorsetech.www.horseApi.model.GaitActivity gaitActivity = new com.sporthorsetech.www.horseApi.model.GaitActivity();
                            gaitActivity.setId(Long.parseLong(g.getStoredObjectId()));
                            gaitActivity.setTimeCreated(g.getTimeCreated());
                            gaitActivity.setFooting(g.getFooting());

                            if (g.getGaits().size() > 0)
                            {
                                for (Gait gt : g.getGaits())
                                {
                                    com.sporthorsetech.www.horseApi.model.Gait gait = new com.sporthorsetech.www.horseApi.model.Gait();
                                    gait.setId(Long.parseLong(gt.getStoredObjectId()));
                                    gait.setName(gt.getName());
                                    gait.setTimeCreated(gt.getTimeCreated());

                                    if (gt.getSteps().size() > 0)
                                    {
                                        for (Step s : gt.getSteps())
                                        {
                                            com.sporthorsetech.www.horseApi.model.Step step = new com.sporthorsetech.www.horseApi.model.Step();
                                            step.setId(Long.parseLong(s.getStoredObjectId()));
                                            step.setTimeCreated(s.getTimeCreated());
                                            step.setHoof(s.getHoof());

                                            AccelerationX accelerationX = new AccelerationX();
                                            accelerationX.setId((Long.parseLong(s.getAccelerationX().getStoredObjectId())));
                                            accelerationX.setAccelerationX(s.getAccelerationX().getAccelerationX());

                                            AccelerationY accelerationY = new AccelerationY();
                                            accelerationY.setId((Long.parseLong(s.getAccelerationY().getStoredObjectId())));
                                            accelerationY.setAccelerationY(s.getAccelerationY().getAccelerationY());

                                            AccelerationZ accelerationZ = new AccelerationZ();
                                            accelerationZ.setId((Long.parseLong(s.getAccelerationZ().getStoredObjectId())));
                                            accelerationZ.setAccelerationZ(s.getAccelerationZ().getAccelerationZ());

                                            Force force = new Force();
                                            force.setId((Long.parseLong(s.getForce().getStoredObjectId())));
                                            force.setForce(s.getForce().getForce());

                                            steps.add(step);
                                        }
                                    }
                                    gait.setSteps(steps);
                                    gaits.add(gait);
                                }
                            }

                            if (g.getBatteryReadings().size() > 0)
                            {
                                for (BatteryReading b : g.getBatteryReadings())
                                {
                                    com.sporthorsetech.www.horseApi.model.BatteryReading batteryReading = new com.sporthorsetech.www.horseApi.model.BatteryReading();
                                    batteryReading.setId(Long.parseLong(b.getStoredObjectId()));
                                    batteryReading.setTimeCreated(b.getTimeCreated());
                                    batteryReading.setPadIdOne(b.getPadIdOne());
                                    batteryReading.setPadOneBatteryVoltage(b.getPadOneBatteryVoltage());
                                    batteryReading.setPadIdTwo(b.getPadIdTwo());
                                    batteryReading.setPadTwoBatteryVoltage(b.getPadTwoBatteryVoltage());
                                    batteryReading.setPadIdThree(b.getPadIdThree());
                                    batteryReading.setPadThreeBatteryVoltage(b.getPadThreeBatteryVoltage());
                                    batteryReading.setPadIdFour(b.getPadIdFour());
                                    batteryReading.setPadFourBatteryVoltage(b.getPadFourBatteryVoltage());

                                    batteryReadings.add(batteryReading);
                                }
                            }
                            gaitActivity.setGaits(gaits);
                            gaitActivity.setBatteryReadings(batteryReadings);
                            gaitActivities.add(gaitActivity);
                        }
                        horse.setHorseHooves(horseHooves);
                        horses.put(String.valueOf(horse.getId()), horse);
                        h.getGaitActivities().clear();
                    }
                }

                try
                {
                    String ownerEmail = LittleDB.getInstance(getApplicationContext()).getString(Constant.HORSE_OWNER_EMAIL);
                    String ownerName = LittleDB.getInstance(getApplicationContext()).getString(Constant.HORSE_OWNER_NAME);
                    Account account = horseApi.checkAccount(ownerEmail).execute();

                    if (!account.getAccount())
                    {
                        Owner owner = new Owner();
                        owner.setId(ownerEmail);
                        owner.setOwnerEmail(ownerEmail);
                        owner.setOwnerName(ownerName);
                        owner.setHorses(horses);

                        horseApi.register(owner).execute();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
