package com.sporthorsetech.horseshoepad;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.punchthrough.bean.sdk.BeanManager;
import com.sporthorsetech.horseshoepad.backend.registration.Registration;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NewHorseFragment.OnFragmentInteractionListener,
        GaitMonitorFragment.OnFragmentInteractionListener, ActivatePadsFragment.OnFragmentInteractionListener,
        DeleteHorseProfileFragment.OnFragmentInteractionListener, HorseProfileFragment.OnFragmentInteractionListener
{
    private Toolbar toolbar;
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Drawer builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_gallery).withName(R.string.new_horse_profile),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_manage).withName(R.string.activate_horseshoe_pads),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_share).withName(R.string.monitor_gait_activity),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_slideshow).withName(R.string.view_gait_data)
                        //new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_send).withName(R.string.delete_all_horse_profiles)
                )
                .withOnDrawerItemClickListener(new OnDrawerItemClickListener()
                {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment fragment = new Fragment();

                        switch (position)
                        {
                            case 0:
                                title = getString(R.string.new_horse_profile);
                                fragment = NewHorseFragment.newInstance();
                                break;
                            case 1:
                                title = getString(R.string.activate_horseshoe_pads);
                                fragment = ActivatePadsFragment.newInstance();
                                break;
                            case 2:
                                title = getString(R.string.monitor_gait_activity);
                                fragment = GaitMonitorFragment.newInstance();
                                break;
                            case 3:
                                title = getString(R.string.view_gait_data);
                                fragment = HorseProfileFragment.newInstance();
                                break;
                            case 4:
                                title = getString(R.string.delete_all_horse_profiles);
                                fragment = DeleteHorseProfileFragment.newInstance();
                                break;
                        }

                        transaction.addToBackStack(title);
                        transaction.replace(R.id.container, fragment, title);
                        transaction.commit();

                        return false;
                    }
                })
                .build();

        builder.setSelection(0);
        builder.openDrawer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                new PostDataAsync().execute("-REGISTRATION_ID-");

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            BeanManager.getInstance().cancelDiscovery();

            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            {
                Fragment fragment = getSupportFragmentManager().getFragments()
                        .get(getSupportFragmentManager().getBackStackEntryCount() - 1);
                fragment.onResume();
            }
        }

        return (super.onKeyDown(keyCode, event));
    }

    @Override
    public void onFragmentInteraction(String title)
    {
        //getSupportActionBar().setTitle(title);
    }

    class PostDataAsync extends AsyncTask<String, Void, Void>
    {
        private Registration regService = null;

        protected void onPreExecute()
        {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://sporthorsetech.appspot.com/_ah/api/");
            builder.setApplicationName("SportHorseTech");
            regService = builder.build();
            Log.d("Pre-Execute", "Building Registration Service......");
        }

        protected Void doInBackground(String... params)
        {
            try
            {
                regService.register(params[0]).execute();

            } catch (IOException e)
            {
                e.printStackTrace();
            }

            Log.d("DoInBackground", "Posting Data......");

            return null;
        }

        protected void onProgressUpdate()
        {
        }

        protected void onPostExecute(String result)
        {
        }
    }
}
