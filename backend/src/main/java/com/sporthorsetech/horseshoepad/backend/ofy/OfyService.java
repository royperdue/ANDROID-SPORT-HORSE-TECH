package com.sporthorsetech.horseshoepad.backend.ofy;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.sporthorsetech.horseshoepad.backend.model.Horse;

public class OfyService
{
    static
    {
        ObjectifyService.register(Horse.class);
    }

    public static Objectify ofy()
    {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory()
    {
        return ObjectifyService.factory();
    }
}
