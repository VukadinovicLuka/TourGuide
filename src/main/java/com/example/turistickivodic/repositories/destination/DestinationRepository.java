package com.example.turistickivodic.repositories.destination;

import com.example.turistickivodic.entities.Destination;

import java.util.List;

public interface DestinationRepository {

    void save(Destination destination);
    List<Destination> findAll();
    boolean delete(Integer id);
    Destination findByName(String name);
    void update(Destination destination);
    Destination getById(int id);
    List<Destination> findWithPagination(int limit, int offset);
    int getTotalCount();
}
