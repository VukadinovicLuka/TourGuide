package com.example.turistickivodic.services;


import com.example.turistickivodic.entities.Activity;
import com.example.turistickivodic.repositories.activity.ActivityRepository;

import javax.inject.Inject;
import java.util.List;

public class ActivityService {

    @Inject
    ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Activity addActivity(Activity activity){
        return activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByDestinationId(int destinationId){
        return activityRepository.getByDestinationId(destinationId);
    }

    public Activity getActivityById(int id) {
        return activityRepository.getById(id);
    }


    public Activity getActivityByDestinationIdAndName(int destinationId, String name) {
        return activityRepository.findByDestinationIdAndName(destinationId, name);
    }

    public boolean deleteActivitiesByDestination(int destinationId) {
        return activityRepository.deleteByDestinationId(destinationId);
    }


}
