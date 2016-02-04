package com.sporthorsetech.horseshoepad.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.sporthorsetech.horseshoepad.backend.constants.AppConstants;
import com.sporthorsetech.horseshoepad.backend.model.Horse;
import com.sporthorsetech.horseshoepad.backend.repository.HorseRepositoryImpl;

import java.util.Calendar;

@Api(
        name = "horseApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = AppConstants.API_OWNER,
                ownerName = AppConstants.API_OWNER,
                packagePath = AppConstants.API_PACKAGE_PATH
        )
)
@ApiClass(resource = "horse",
        scopes = {AppConstants.EMAIL_SCOPE},
        clientIds = {AppConstants.ANDROID_CLIENT_ID, AppConstants.WEB_CLIENT_ID,
                com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
        audiences = {AppConstants.AUDIENCE_ID}
)
public class HorseEndpoint
{

    @ApiMethod(name = "horse.by.id", httpMethod = ApiMethod.HttpMethod.GET)
    public Horse findById(User user, @Named("id") Long id) throws BadRequestException, UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("User not found");
        }
        if (id == null)
        {
            throw new BadRequestException("Missing attribute id");
        }
        return HorseRepositoryImpl.getInstance().findByIdDAO(id);
    }

    @ApiMethod(name = "horses.list", httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Horse> listStories(User user) throws UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("User not found");
        }
        return HorseRepositoryImpl.getInstance().findAllStoriesDAO();
    }

    @ApiMethod(name = "horse.save", httpMethod = ApiMethod.HttpMethod.POST)
    public Horse save(User user, Horse horse) throws UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("User not found");
        }
        if (horse == null)
        {
            throw new UnauthorizedException("Request is invalid");
        }
        horse.setRegDate(Calendar.getInstance().getTime());
        return HorseRepositoryImpl.getInstance().save(horse);
    }

    @ApiMethod(name = "horse.remove", httpMethod = ApiMethod.HttpMethod.DELETE)
    public Horse remove(User user, @Named("id") Long id) throws NotFoundException, BadRequestException, UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("User not found");
        }
        if (id == null)
        {
            throw new BadRequestException("Missing attribute id");
        }
        Horse horse = findById(user, id);
        if (horse == null)
        {
            throw new NotFoundException("Cannot find horse to remove");
        }
        return HorseRepositoryImpl.getInstance().remove(horse);
    }

    @ApiMethod(name = "horse.update", httpMethod = ApiMethod.HttpMethod.PUT)
    public Horse update(User user, Horse horse) throws BadRequestException, UnauthorizedException
    {
        if (user == null)
        {
            throw new UnauthorizedException("User not found");
        }
        if (horse == null || horse.getId() == null)
        {
            throw new BadRequestException("Missing attribute id");
        }
        return HorseRepositoryImpl.getInstance().update(horse);
    }
}
