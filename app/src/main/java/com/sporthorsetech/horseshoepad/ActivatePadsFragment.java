package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.ScratchBank;
import com.sporthorsetech.horseshoepad.utility.equine.ClickableArea;
import com.sporthorsetech.horseshoepad.utility.equine.ClickableAreasImage;
import com.sporthorsetech.horseshoepad.utility.equine.HorseFoot;
import com.sporthorsetech.horseshoepad.utility.equine.OnClickableAreaClickedListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ActivatePadsFragment extends Fragment implements OnClickableAreaClickedListener
{
    private OnFragmentInteractionListener mListener;
    PhotoViewAttacher attacher;
    private final List<Bean> beans = new ArrayList<>();

    public ActivatePadsFragment()
    {
        // Required empty public constructor
    }

    public static ActivatePadsFragment newInstance()
    {
        ActivatePadsFragment fragment = new ActivatePadsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final BeanListener beanListener = new BeanListener()
        {
            @Override
            public void onConnected()
            {
            }

            @Override
            public void onError(BeanError berr)
            {
                System.out.println("Bean has errors..");
            }

            @Override
            public void onConnectionFailed()
            {
                System.out.println("Bean connection failed");
            }

            @Override
            public void onDisconnected()
            {
                System.out.println("Bean disconnected");
            }

            @Override
            public void onScratchValueChanged(ScratchBank bank, byte[] value)
            {
            }

            @Override
            public void onSerialMessageReceived(byte[] data)
            {
            }
        };

        BeanDiscoveryListener listener = new BeanDiscoveryListener()
        {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi)
            {
                beans.add(bean);
                bean.connect(getActivity(), beanListener);
            }

            @Override
            public void onDiscoveryComplete()
            {
                RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.radioButtonGroupEquine);

                System.out.println("Total beans discovered: " + beans.size());

                for (int i = 0; i < radioGroup.getChildCount(); i++)
                {
                    ((RadioButton) radioGroup.getChildAt(i)).setText(beans.get(i).getDevice().getName());
                }
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_activate_pads, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.horse_trotting);

        attacher = new PhotoViewAttacher(image);
        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(attacher, this);

        List<ClickableArea> clickableAreas = getClickableAreas();
        clickableAreasImage.setClickableAreas(clickableAreas);

        return view;
    }

    @NonNull
    private List<ClickableArea> getClickableAreas()
    {

        List<ClickableArea> clickableAreas = new ArrayList<>();

        // ClickableArea(x, y, w, h, object);
        //clickableAreas.add(new ClickableArea(20, 20, 50, 50, new HorseFoot("Gustavo", "Fring")));


        return clickableAreas;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(getString(R.string.activate_horseshoe_pads));
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickableAreaTouched(Object item)
    {
        String text = "hit";
        if (item instanceof String)
        {
            text = (String) item;
        } else if (item instanceof HorseFoot)
        {
            //text = ((HorseFoot) item).getFirstName() + " " + ((HorseFoot) item).getLastName();
        }
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
