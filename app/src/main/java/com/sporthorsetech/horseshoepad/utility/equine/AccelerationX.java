package com.sporthorsetech.horseshoepad.utility.equine;

import android.os.Parcel;
import android.os.Parcelable;

import com.sporthorsetech.horseshoepad.utility.persist.Database;
import com.sporthorsetech.horseshoepad.utility.persist.SearchableTagValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by royperdue on 1/14/16.
 */
public class AccelerationX implements Database.StoredObject, Parcelable
{
    String id;
    Long accelerationX;

    protected AccelerationX(Parcel in)
    {
        id = in.readString();
    }

    public static final Creator<AccelerationX> CREATOR = new Creator<AccelerationX>()
    {
        @Override
        public AccelerationX createFromParcel(Parcel in)
        {
            return new AccelerationX(in);
        }

        @Override
        public AccelerationX[] newArray(int size)
        {
            return new AccelerationX[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeLong(accelerationX);
    }

    public enum TYPE implements Database.StoredObject.TYPE
    {
        accelerationX(AccelerationX.class);

        private Class cls;

        TYPE(Class cls)
        {
            this.cls = cls;
        }

        @Override
        public Class getTypeClass()
        {
            return cls;
        }

        @Override
        public String getTypeName()
        {
            return name();
        }
    }

    public AccelerationX(String id, Long accelerationX)
    {
        this.id = id;
        this.accelerationX = accelerationX;
    }

    public TYPE getStoredObjectType()
    {
        return TYPE.accelerationX;
    }

    @Override
    public String getStoredObjectId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Long getAccelerationX()
    {
        return accelerationX;
    }

    public void setAccelerationX(Long accelerationX)
    {
        this.accelerationX = accelerationX;
    }

    // Return a list of tags that you want to be able to search for this object by.
    public List<SearchableTagValuePair> getStoredObjectSearchableTags()
    {
        List<SearchableTagValuePair> retval = new LinkedList<>();
        retval.add(new SearchableTagValuePair("id", id));

        return retval;
    }

    @Override
    public Long getStoredObjectTimestampMillis()
    {
        return System.currentTimeMillis() / 1000;
    }
}
