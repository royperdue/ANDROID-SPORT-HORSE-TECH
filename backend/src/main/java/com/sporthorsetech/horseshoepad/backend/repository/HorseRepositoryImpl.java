package com.sporthorsetech.horseshoepad.backend.repository;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.sporthorsetech.horseshoepad.backend.model.Horse;

import java.util.List;

import static com.sporthorsetech.horseshoepad.backend.ofy.OfyService.ofy;

public class HorseRepositoryImpl implements IfHorseRepository
{

    private static HorseRepositoryImpl horseRepository = null;

    public static synchronized HorseRepositoryImpl getInstance()
    {
        if (null == horseRepository)
        {
            horseRepository = new HorseRepositoryImpl();
        }
        return horseRepository;
    }

    @Override
    public Horse save(Horse horse)
    {
        ofy().save().entity(horse).now();
        return horse;
    }

    @Override
    public Horse update(Horse horse)
    {
        Horse horseToUpdate = ofy().load().key(Key.create(Horse.class,
                horse.getId())).now();
        horseToUpdate.setHorseName(horse.getHorseName());
        horseToUpdate.setRegDate(horse.getRegDate());
        ofy().save().entity(horseToUpdate).now();
        return horse;
    }

    @Override
    public Horse findByIdDAO(Long id)
    {
        return ofy().load().type(Horse.class).id(id).now();
    }

    @Override
    public CollectionResponse<Horse> findAllStoriesDAO()
    {
        Query<Horse> query = ofy().load().type(Horse.class);
        List<Horse> stories = query.list();
        return CollectionResponse.<Horse>builder().setItems(stories).build();
    }

    @Override
    public Horse remove(Horse horse)
    {
        ofy().delete().entity(horse).now();
        return horse;
    }
}
