package com.sporthorsetech.horseshoepad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.sporthorsetech.horseshoepad.utility.Constant;

public class GraphActivity extends AppCompatActivity implements GraphActivityFragmentAcceleration.OnFragmentInteractionListener,
        GraphActivityFragmentForce.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        String title = "";
        Bundle extras = getIntent().getExtras();
        int value;

        if (extras != null)
        {
            value = extras.getInt(Constant.GRAPH_FRAGMENT_INDICATOR);

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = new Fragment();

            if (value == Constant.GRAPH_FRAGMENT_ACCELERATION)
            {
                title = getString(R.string.acceleration_graph_data);
                fragment = GraphActivityFragmentAcceleration.newInstance();
            }
            else if (value == Constant.GRAPH_FRAGMENT_FORCE)
            {
                title = getString(R.string.force_graph_data);
                fragment = GraphActivityFragmentForce.newInstance();
            }

            transaction.addToBackStack(title);
            transaction.replace(R.id.container, fragment, title);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(String title)
    {

    }
}
