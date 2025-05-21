package com.example.turistickivodic.services;

import com.example.turistickivodic.entities.Destination;
import com.example.turistickivodic.entities.User;
import com.example.turistickivodic.repositories.destination.DestinationRepository;

import javax.inject.Inject;
import java.util.List;

public class DestinationService {

    @Inject
    private DestinationRepository destinationRepository;

    public List<Destination> getAllUsers() {
        return destinationRepository.findAll();
    }

    public void addDestination(Destination destination){
        destinationRepository.save(destination);
    }

    public boolean deleteDestination(Integer id) {
        return destinationRepository.delete(id);
    }

    public Destination getDestinationByName(String name) {
        return destinationRepository.findByName(name);
    }

    public void updateDestination(Destination destination) {
        destinationRepository.update(destination);
    }

    public Destination getDestinationById(int id) {
        return destinationRepository.getById(id);
    }

    public List<Destination> getDestinationsWithPagination(int page, int size) {
        int offset = (page - 1) * size;
        return destinationRepository.findWithPagination(size, offset);
    }

    public int getTotalDestinationsCount() {
        return destinationRepository.getTotalCount();
    }

}
