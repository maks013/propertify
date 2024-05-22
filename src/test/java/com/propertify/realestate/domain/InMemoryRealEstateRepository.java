package com.propertify.realestate.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryRealEstateRepository implements RealEstateRepository {

    private Map<Long, RealEstate> inMemoryRepo = new ConcurrentHashMap<>();

    @Override
    public List<RealEstate> findAll() {
        return List.copyOf(inMemoryRepo.values());
    }

    @Override
    public Optional<RealEstate> findById(Long id) {
        return Optional.ofNullable(inMemoryRepo.get(id));
    }

    @Override
    public List<RealEstate> findAllByCity(String city) {
        return inMemoryRepo.values().stream()
                .filter(realEstate -> realEstate.getCity().equalsIgnoreCase(city))
                .toList();
    }

    @Override
    public List<RealEstate> findAllInPriceRange(Integer startPrice, Integer endPrice) {
        return inMemoryRepo.values().stream()
                .filter(realEstate -> realEstate.getPrice() >= startPrice && realEstate.getPrice() <= endPrice)
                .toList();
    }

    @Override
    public List<RealEstate> findAllInPriceBySquareMeterRange(Integer startPrice, Integer endPrice) {
        return inMemoryRepo.values().stream()
                .filter(realEstate -> realEstate.getPricePerSquareMeter() >= startPrice && realEstate.getPricePerSquareMeter() <= endPrice)
                .toList();
    }

    @Override
    public List<RealEstate> findAllInMetricAreaRange(Integer startMetricArea, Integer endMetricArea) {
        return inMemoryRepo.values().stream()
                .filter(realEstate -> realEstate.getMetricArea() >= startMetricArea && realEstate.getMetricArea() <= endMetricArea)
                .toList();
    }

    @Override
    public List<RealEstate> findAllByAllParams(Integer startPrice, Integer endPrice, Integer startMetricArea, Integer endMetricArea, String cityName) {
        return inMemoryRepo.values().stream()
                .filter(realEstate -> realEstate.getPrice() >= startPrice && realEstate.getPrice() <= endPrice)
                .filter(realEstate -> realEstate.getMetricArea() >= startMetricArea && realEstate.getMetricArea() <= endMetricArea)
                .filter(realEstate -> realEstate.getCity().equalsIgnoreCase(cityName))
                .toList();
    }

    public void addRealEstate(RealEstate realEstate) {
        inMemoryRepo.put(realEstate.getId(), realEstate);
    }
}
