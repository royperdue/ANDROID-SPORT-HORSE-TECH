package com.sporthorsetech.horseshoepad.backend.service;

import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.sporthorsetech.horseshoepad.backend.model.Owner;

import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by royperdue on 2/19/16.
 */
public class GetService
{
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static final Logger logger = Logger.getLogger(GetService.class.getName());
    private static final int DEFAULT_LIST_LIMIT = 20;

    public GetService()
    {
    }

    public Owner getOwner(String ownerEmail) throws NotFoundException
    {
        logger.info("Getting Teacher with ID: " + ownerEmail);
        Owner owner = ofy().load().type(Owner.class).id(ownerEmail).now();
        if (owner == null)
        {
            throw new NotFoundException("Your data was not found.");
        }
        return owner;
    }
}
