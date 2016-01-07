package com.sporthorsetech.horseshoepad;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sporthorsetech.horseshoepad.utility.Constant;
import com.sporthorsetech.horseshoepad.utility.equine.Horse;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment
{
    // The content this fragment is presenting.

    private Horse mItem;

    public ItemDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constant.ARG_ITEM_ID))
        {
            // Load the content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ItemListActivity.horses.get(Integer.parseInt(getArguments().getString(Constant.ARG_ITEM_ID)) - 1);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the content as text in a TextView.
        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.getName() + " " + mItem.getBreed() + " " + mItem.getDiscipline());
        }

        return rootView;
    }
}
