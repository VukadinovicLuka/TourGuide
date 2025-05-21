package com.example.turistickivodic.repositories.activity;

import com.example.turistickivodic.entities.Activity;
import java.util.List;

public interface ActivityRepository {

    List<Activity> findAll();
    Activity save(Activity activity);
    List<Activity> getByDestinationId(int id);
    Activity findByDestinationIdAndName(int destinationId, String name);
    boolean deleteByDestinationId(int destinationId);
    Activity getById(int id);

}
