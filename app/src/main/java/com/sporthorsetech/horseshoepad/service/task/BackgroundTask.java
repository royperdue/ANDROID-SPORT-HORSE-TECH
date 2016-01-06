package com.sporthorsetech.horseshoepad.service.task;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.sporthorsetech.horseshoepad.service.Task;

public class BackgroundTask extends Task
{
    public BackgroundTask()
    {
        super();
    }

    public BackgroundTask(Bundle params)
    {
        super(params);
    }

    public BackgroundTask(Parcel in)
    {
        super(in);
    }

    public final static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>()
    {

        public BackgroundTask createFromParcel(Parcel in)
        {
            return new BackgroundTask(in);
        }

        public BackgroundTask[] newArray(int size)
        {
            return new BackgroundTask[size];
        }
    };

    @Override
    public void execute(TaskProgressListener progressListener, Task task)
    {
        try
        {


        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}