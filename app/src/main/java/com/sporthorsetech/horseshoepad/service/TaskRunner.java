package com.sporthorsetech.horseshoepad.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.HashMap;

//Singleton class, takes a class, intentservice and listener.
//Stores the listeners in a hashmap by task id.
//Contains an inner class called ResultHandler that notifies listeners periodically.
public class TaskRunner
{
    public static final String TAG = TaskRunner.class.getName();
    private static TaskRunner instance;
    private Context context;
    private HashMap<String, TaskRunnerListener> listeners;

    public static TaskRunner getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new TaskRunner(context);
        }
        return instance;
    }

    public void runTaskByService(Task task, Class<?> serviceClass, TaskRunnerListener listener)
    {
        final String taskId = task.toString();
        task.setId(taskId);
        // Saves the listener in a hashmap.
        this.listeners.put(taskId, listener);
        // Creates an intent to start the IntentService.
        Intent intent = new Intent(context, serviceClass);
        // Adds a ResultHandler to the intent as EXTRA_KEY_RECEIVER
        intent.putExtra(Constants.EXTRA_KEY_RECEIVER, new ResultHandler(null));
        // Adds the task to the intent.
        intent.putExtra(Task.EXTRA_KEY_TASK, task);
        // Starts the IntentService.
        context.startService(intent);
    }

    public void stopService(Class<?> serviceClass)
    {
        Intent intent = new Intent(context, serviceClass);
        context.stopService(intent);
    }

    public interface TaskRunnerListener
    {
        public void onTaskCompleted(Bundle result);
        public void onTaskProgressUpdated(int progress);
    }

    private TaskRunner(Context context)
    {
        this.context = context;
        this.listeners = new HashMap<String, TaskRunnerListener>();
    }

    private class ResultHandler extends ResultReceiver
    {
        public ResultHandler(Handler handler)
        {
            super(handler);
        }
        // Gets the Task back after it has been run in onHandleIntent in the IntentService.
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            // Gets the Task from the bundle.
            final Task task = resultData.getParcelable(Task.EXTRA_KEY_TASK);
            final String taskId = task.getId();
            // Gets the listener from the hashmap.
            final TaskRunnerListener listener = listeners.get(taskId);
            if (listener != null)
            {
                if (resultCode == Constants.RESULT_CODE_TASK_COMPLETED)
                {
                    // Calls the onTaskCompleted method in the listener and passes the whole Task which is contained
                    // in a bundle.
                    listener.onTaskCompleted(resultData);
                    listeners.remove(taskId);
                }
                if (resultCode == Constants.RESULT_CODE_TASK_PROGRESS_UPDATED)
                {
                    listener.onTaskProgressUpdated(resultData.getInt(Constants.EXTRA_KEY_PROGRESS_UPDATED));
                }
            }
        }
    }
}
