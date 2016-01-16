package com.sporthorsetech.horseshoepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.punchthrough.bean.sdk.BeanManager;
import com.sporthorsetech.horseshoepad.utility.Constant;

public class GraphActivity extends FragmentActivity implements GraphActivityFragmentAcceleration.OnFragmentInteractionListener,
        GraphActivityFragmentForce.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph);

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
            transaction.replace(R.id.content_frame, fragment, title);
            transaction.commit();
        }
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
            else
            {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        }

        return (super.onKeyDown(keyCode, event));
    }

    @Override
    public void onFragmentInteraction(String title)
    {

    }
}
