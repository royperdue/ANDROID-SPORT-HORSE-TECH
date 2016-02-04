package com.sporthorsetech.horseshoepad.backend.repository;

import com.google.api.server.spi.response.CollectionResponse;
import com.sporthorsetech.horseshoepad.backend.model.Horse;

public interface IfHorseRepository
{

    Horse save(Horse story);
    Horse update(Horse story);
    Horse findByIdDAO(Long id);
    CollectionResponse<Horse> findAllStoriesDAO();
    Horse remove(Horse story);
}
