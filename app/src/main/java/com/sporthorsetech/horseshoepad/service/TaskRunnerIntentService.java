package com.sporthorsetech.horseshoepad.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

// Implements the TaskProgressListener interface in the abstract class Task, onProgressUpdate.
// onProgressUpdate sends the results back to ResultReceiver which sends it through to the listener
// in the activity that implements TaskRunner.TaskRunnerListener.
// TaskRunner.TaskRunnerListener is an interface in TaskRunner.
public class TaskRunnerIntentService extends IntentService implements Task.TaskProgressListener
{
    public static final String TAG = TaskRunnerIntentService.class.getName();

    private ResultReceiver receiver;

    public TaskRunnerIntentService()
    {
        this(TaskRunnerIntentService.class.getName());
    }

    public TaskRunnerIntentService(String name)
    {
        super(name);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "Destroying IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        // Gets the ResultHandler.
        receiver = intent.getParcelableExtra(Constants.EXTRA_KEY_RECEIVER);
        final Bundle b = new Bundle();
        // Gets the Task.
        final Task task = intent.getParcelableExtra(Task.EXTRA_KEY_TASK);
        // Calls execute in the Task.
        task.execute(this, task);
        // Adds the whole task to a bundle.
        b.putParcelable(Task.EXTRA_KEY_TASK, task);
        // Sends to onReceiveResult in TaskRunner.
        receiver.send(Constants.RESULT_CODE_TASK_COMPLETED, b);
    }

    @Override
    public void onProgressUpdate(int progress, Task task)
    {
        final Bundle b = new Bundle();
        b.putParcelable(Task.EXTRA_KEY_TASK, task);
        b.putInt(Constants.EXTRA_KEY_PROGRESS_UPDATED, progress);
        receiver.send(Constants.RESULT_CODE_TASK_PROGRESS_UPDATED, b);
    }
}
