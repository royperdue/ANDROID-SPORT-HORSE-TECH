package com.sporthorsetech.horseshoepad.service;

public abstract interface Constants
{
    public static final String EXTRA_KEY_RECEIVER = Constants.class.getName() + "receiver";
    public static final String EXTRA_KEY_TASK_COMPLETED = Constants.class.getName() + "task_completed";
    public static final String EXTRA_KEY_PROGRESS_UPDATED = Constants.class.getName() + "progress_updated";
    public static final int RESULT_CODE_TASK_COMPLETED = "task_completed".hashCode();
    public static final int RESULT_CODE_TASK_PROGRESS_UPDATED = "task_progress_updated".hashCode();
}
