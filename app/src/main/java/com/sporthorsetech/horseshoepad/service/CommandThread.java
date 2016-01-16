package com.sporthorsetech.horseshoepad.service;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by royperdue on 1/16/16.
 */
public class CommandThread implements Runnable
{
    Thread th;
    private List<Bean> beans = new ArrayList<>();
    String command;

    public CommandThread(List<Bean> beans, String command)
    {
        this.beans = beans;
        this.command = command;
        th = new Thread(this);
        th.setPriority(Thread.MAX_PRIORITY);
        th.start();
    }

    @Override
    public void run()
    {
        for (Bean bean : beans)
        {
            bean.setScratchData(ScratchBank.BANK_5, command);
        }
    }
}
