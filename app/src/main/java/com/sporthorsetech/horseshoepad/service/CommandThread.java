package com.sporthorsetech.horseshoepad.service;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by royperdue on 1/8/16.
 */
public class CommandThread implements Runnable
{
    private Thread th;
    private Map<String, Bean> beans = new HashMap<>();
    private String command;

    public CommandThread(HashMap<String, Bean> beans, String command)
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
        Iterator<Map.Entry<String, Bean>> entries = beans.entrySet().iterator();

        while (entries.hasNext())
        {
            Map.Entry<String, Bean> entry = entries.next();
            System.out.println("MAP KEY " + entry.getKey());
            entry.getValue().setScratchData(ScratchBank.BANK_5, command);
        }
    }
}
