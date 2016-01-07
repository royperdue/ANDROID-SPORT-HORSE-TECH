package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NewHorseFragment.OnFragmentInteractionListener,
        WelcomeFragment.OnFragmentInteractionListener, GaitMonitorFragment.OnFragmentInteractionListener,
        ActivatePadsFragment.OnFragmentInteractionListener, DeleteHorseProfileFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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
                if(position == 0)
                {
                    while (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, WelcomeFragment.newInstance("Welcome", "Back"))
                            .addToBackStack(getString(R.string.welcome)).commit();
                }
                else if(position == 1)
                {
                    while (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, NewHorseFragment.newInstance())
                            .addToBackStack(getString(R.string.new_horse_profile)).commit();
                }
                else if (position == 2)
                {
                    while (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, ActivatePadsFragment.newInstance())
                            .addToBackStack(getString(R.string.activate_horseshoe_pads)).commit();
                }
                else if (position == 3)
                {
                    while (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, GaitMonitorFragment.newInstance())
                            .addToBackStack(getString(R.string.monitor_gait_activity)).commit();
                }
                else if (position == 4)
                {
                    startActivity(new Intent(getBaseContext(), ItemListActivity.class));
                }
                else
                {
                    while (getSupportFragmentManager().getBackStackEntryCount() > 0)
                    {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, DeleteHorseProfileFragment.newInstance())
                            .addToBackStack(getString(R.string.delete_all_horse_profiles)).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            {
                getSupportFragmentManager().popBackStackImmediate();
            }
            else
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
