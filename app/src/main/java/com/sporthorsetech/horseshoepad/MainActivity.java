package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity implements NewHorseFragment.OnFragmentInteractionListener,
        WelcomeFragment.OnFragmentInteractionListener, GaitMonitorFragment.OnFragmentInteractionListener,
        ActivatePadsFragment.OnFragmentInteractionListener, DeleteHorseProfileFragment.OnFragmentInteractionListener
{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        /*Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerAdapter(
                toolbar.getContext(),
                new String[]{
                        getString(R.string.welcome),
                        getString(R.string.new_horse_profile),
                        getString(R.string.activate_horseshoe_pads),
                        getString(R.string.monitor_gait_activity),
                        getString(R.string.view_gait_data),
                        getString(R.string.delete_all_horse_profiles)
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    while (getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStackImmediate();
                    }
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, WelcomeFragment.newInstance())
                            .addToBackStack(getString(R.string.welcome)).commit();
                } else if (position == 1)
                {
                    while (getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStackImmediate();
                    }
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, NewHorseFragment.newInstance())
                            .addToBackStack(getString(R.string.new_horse_profile)).commit();
                } else if (position == 2)
                {
                    while (getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStackImmediate();
                    }
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, ActivatePadsFragment.newInstance())
                            .addToBackStack(getString(R.string.activate_horseshoe_pads)).commit();
                } else if (position == 3)
                {
                    while (getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStackImmediate();
                    }
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, GaitMonitorFragment.newInstance())
                            .addToBackStack(getString(R.string.monitor_gait_activity)).commit();
                } else if (position == 4)
                {
                    startActivity(new Intent(getBaseContext(), ItemListActivity.class));
                } else
                {
                    while (getFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getFragmentManager().popBackStackImmediate();
                    }
                    getFragmentManager().beginTransaction()
                            .add(R.id.container, DeleteHorseProfileFragment.newInstance())
                            .addToBackStack(getString(R.string.delete_all_horse_profiles)).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });*/

        Drawer builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_camera).withName(R.string.welcome),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_gallery).withName(R.string.new_horse_profile),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_manage).withName(R.string.activate_horseshoe_pads),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_share).withName(R.string.monitor_gait_activity),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_slideshow).withName(R.string.view_gait_data),
                        new SecondaryDrawerItem().withIcon(R.drawable.ic_menu_send).withName(R.string.delete_all_horse_profiles)
                )
                .withOnDrawerItemClickListener(new OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        Fragment fragment = new Fragment();

                        switch (position)
                        {
                            case 0:
                                fragment = WelcomeFragment.newInstance();
                                break;
                            case 1:
                                fragment = NewHorseFragment.newInstance();
                                break;
                            case 2:
                                fragment = ActivatePadsFragment.newInstance();
                                break;
                            case 3:
                                fragment = GaitMonitorFragment.newInstance();
                                break;
                            case 4:
                                startActivity(new Intent(MainActivity.this, ItemListActivity.class));
                                break;
                            case 5:
                                fragment = DeleteHorseProfileFragment.newInstance();
                                break;
                        }

                        transaction.replace(R.id.container, fragment);
                        transaction.commit();

                        return false;
                    }
                }).build();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String title)
    {
        //getSupportActionBar().setTitle(title);
    }

    private static class SpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter
    {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public SpinnerAdapter(Context context, String[] objects)
        {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            View view;

            if (convertView == null)
            {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else
            {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme()
        {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme)
        {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }
}
