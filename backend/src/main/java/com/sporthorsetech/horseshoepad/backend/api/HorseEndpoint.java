package com.sporthorsetech.horseshoepad.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;
import com.sporthorsetech.horseshoepad.backend.constants.AppConstants;
import com.sporthorsetech.horseshoepad.backend.model.AccelerationX;
import com.sporthorsetech.horseshoepad.backend.model.AccelerationY;
import com.sporthorsetech.horseshoepad.backend.model.AccelerationZ;
import com.sporthorsetech.horseshoepad.backend.model.Account;
import com.sporthorsetech.horseshoepad.backend.model.BatteryReading;
import com.sporthorsetech.horseshoepad.backend.model.Force;
import com.sporthorsetech.horseshoepad.backend.model.Gait;
import com.sporthorsetech.horseshoepad.backend.model.GaitActivity;
import com.sporthorsetech.horseshoepad.backend.model.Horse;
import com.sporthorsetech.horseshoepad.backend.model.HorseHoof;
import com.sporthorsetech.horseshoepad.backend.model.Horses;
import com.sporthorsetech.horseshoepad.backend.model.Owner;
import com.sporthorsetech.horseshoepad.backend.model.Step;
import com.sporthorsetech.horseshoepad.backend.service.GetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Api(
        name = "horseApi",
        version = AppConstants.VERSION,
        resource = AppConstants.RESOURCE,
        description = AppConstants.DESCRIPTION, namespace = @ApiNamespace(
        ownerDomain = AppConstants.NAMESPACE_OWNER_DOMAIN,
        ownerName = AppConstants.NAMESPACE_OWNER_NAME,
        packagePath = AppConstants.NAMESPACE_PACKAGE_PATH), clientIds = {
        AppConstants.WEB_CLIENT_ID, AppConstants.ANDROID_CLIENT_ID_PRO},
        audiences = {AppConstants.AUDIENCE}, scopes = {AppConstants.EMAIL_SCOPE})
public class HorseEndpoint
{
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static final Logger logger = Logger.getLogger(HorseEndpoint.class.getName());
    private GetService getService = new GetService();

    static
    {
        ObjectifyService.register(Owner.class);
        ObjectifyService.register(Horse.class);
        ObjectifyService.register(HorseHoof.class);
        ObjectifyService.register(Gait.class);
        ObjectifyService.register(GaitActivity.class);
        ObjectifyService.register(Step.class);
        ObjectifyService.register(Force.class);
        ObjectifyService.register(BatteryReading.class);
        ObjectifyService.register(AccelerationX.class);
        ObjectifyService.register(AccelerationY.class);
        ObjectifyService.register(AccelerationZ.class);
    }

    @ApiMethod(name = "checkAccount")
    public Account checkAccount(@Named("ownerEmail") String ownerEmail, User user) throws UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        Account account = new Account();

        Owner owner = ofy().load().type(Owner.class).id(ownerEmail).now();
        if (owner == null)
        {
            account.setAccount(false);

            return account;
        }
        account.setAccount(true);

        return account;
    }

    @ApiMethod(
            name = "register",
            path = "owner",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void register(Owner owner, User user) throws UnauthorizedException, NotFoundException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        if (checkOwnerExists(owner.getId()) == false)
        {
            ofy().save().entity(owner).now();
            logger.info("OWNER-CREATED.");
        }
    }

    @ApiMethod(
            name = "insertHorse",
            path = "horse",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insertHorse(@Named("ownerEmail") String ownerEmail, Horse horse, User user) throws NotFoundException, UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        if (checkOwnerExists(ownerEmail) == true)
        {
            Owner owner = getService.getOwner(ownerEmail);
            Map<String, Horse> horses = owner.getHorses();
            horses.put(String.valueOf(horse.getId()), horse);
            owner.setHorses(horses);
            ofy().save().entity(owner).now();
            logger.info("HORSE-ADDED.");
        }
        else if (checkOwnerExists(ownerEmail) == false)
        {
            Owner o = new Owner();
            o.setId(ownerEmail);
            o.setOwnerEmail(ownerEmail);
            o.setHorses(new HashMap<String, Horse>());
            ofy().save().entity(o).now();
            logger.info("OWNER-CREATED.");

            Owner owner = getService.getOwner(ownerEmail);
            Map<String, Horse> horses = owner.getHorses();
            horses.put(String.valueOf(horse.getId()), horse);
            owner.setHorses(horses);
            ofy().save().entity(owner).now();
            logger.info("HORSE-ADDED.");
        }
    }

    @ApiMethod(
            name = "insertHorses",
            path = "horses",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insertHorses(@Named("ownerEmail") String ownerEmail, Horses horses, User user) throws NotFoundException, UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        if (checkOwnerExists(ownerEmail) == true)
        {
            Owner owner = getService.getOwner(ownerEmail);
            Map<String, Horse> horsesMap = owner.getHorses();

            for (Horse h : horses.getHorseList())
            {
                horsesMap.put(String.valueOf(h.getId()), h);
            }

            owner.setHorses(horsesMap);
            ofy().save().entity(owner).now();
            logger.info("HORSE-ADDED.");
        }
    }

    @ApiMethod(
            name = "getOwner",
            path = "owner/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Owner getOwner(@Named("ownerEmail") String ownerEmail, User user) throws UnauthorizedException, NotFoundException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        //Removed removed = ofy().load().type(Removed.class).id("REMOVED").now();

        //if(removed.checkRemoved(teacherEmail) == false)
        //{
        return getService.getOwner(ownerEmail);
        //}
    }

    @ApiMethod(
            name = "getHorse",
            path = "horse/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Horse getHorse(@Named("ownerEmail") String ownerEmail, @Named("horseName") String horseName, User user) throws UnauthorizedException, NotFoundException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        Horse h = null;

        if (checkOwnerExists(ownerEmail) == true)
        {
            Owner owner = getService.getOwner(ownerEmail);
            Map<String, Horse> horses = owner.getHorses();

            for (Horse horse : horses.values())
            {
                if (horse.getName().equals(horseName))
                {
                    h = horse;
                    break;
                }
            }
        }
        return h;
    }

    @ApiMethod(
            name = "getHorses",
            path = "horse",
            httpMethod = ApiMethod.HttpMethod.GET)
    public List<Horse> getHorses(@Named("ownerEmail") String ownerEmail, User user) throws UnauthorizedException, NotFoundException
    {
        if (user == null)
        {
            throw new UnauthorizedException("The user is not authorized.");
        }

        List<Horse> horseList = null;

        if (checkOwnerExists(ownerEmail) == true)
        {
            Owner owner = getService.getOwner(ownerEmail);
            Map<String, Horse> horses = owner.getHorses();

            horseList = new ArrayList<Horse>(horses.values());
        }

        return horseList;
    }

    private boolean checkOwnerExists(String ownerEmail)
    {
        Owner owner = ofy().load().type(Owner.class).filter("ownerEmail", ownerEmail).first().now();

        if (owner != null)
            return true;
        else
            return false;
    }
}
