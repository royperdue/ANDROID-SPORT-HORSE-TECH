package com.sporthorsetech.horseshoepad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
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

public class ActivatePadsFragment extends Fragment implements OnClickableAreaClickedListener, ISimpleDialogCancelListener
{
    private OnFragmentInteractionListener mListener;
    private PhotoViewAttacher attacher;
    private List<ClickableArea> clickableAreas;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ImageView imageView;
    private ImageButton rightHind;
    private ImageButton leftHind;
    private ImageButton leftFront;
    private ImageButton rightFront;
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

        radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonGroupEquine);
        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.horse_trotting_top);
        rightFront = (ImageButton) view.findViewById(R.id.imageButtonRF);
        rightFront.setImageResource(R.drawable.rf);
        rightHind = (ImageButton) view.findViewById(R.id.imageButtonRH);
        rightHind.setImageResource(R.drawable.rh);
        leftFront = (ImageButton) view.findViewById(R.id.imageButtonLF);
        leftFront.setImageResource(R.drawable.lf);
        leftHind = (ImageButton) view.findViewById(R.id.imageButtonLH);
        leftHind.setImageResource(R.drawable.lh);

        attacher = new PhotoViewAttacher(imageView);
        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(attacher, this);

        clickableAreas = getClickableAreas();
        clickableAreasImage.setClickableAreas(clickableAreas);

        return view;
    }

    @NonNull
    private List<ClickableArea> getClickableAreas()
    {
        this.clickableAreas = new ArrayList<>();
        int measuredWidth = this.imageView.getWidth();
        int measuredHeight = this.imageView.getHeight();

        int leftHindX = (measuredWidth / 2) - (measuredWidth / 10);
        int leftFrontX = (measuredWidth / 2) + (measuredWidth / 10);
        int rightHindX = (measuredWidth / 2) - (measuredWidth / 6);
        int rightFrontX = (measuredWidth / 2) + (measuredWidth / 6);
        int y = measuredHeight - ((measuredHeight / 2) + (measuredHeight / 3));
        int width = measuredWidth / 18;
        int height = measuredHeight / 16;

        // ClickableArea(x, y, w, h, object);
        this.clickableAreas.add(new ClickableArea(leftFrontX, y, width, height, new HorseFoot("1", "LF")));
        this.clickableAreas.add(new ClickableArea(leftHindX, y, width, height, new HorseFoot("2", "LH")));
        this.clickableAreas.add(new ClickableArea(rightFrontX, y, width, height, new HorseFoot("3", "RF")));
        this.clickableAreas.add(new ClickableArea(rightHindX, y, width, height, new HorseFoot("4", "RH")));

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
            text = ((HorseFoot) item).getFoot();

            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            //text = ((HorseFoot) item).getFirstName() + " " + ((HorseFoot) item).getLastName();
        }
    }

    @Override
    public void onCancelled(int requestCode)
    {

    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
