package ru.job4j.repository;

import ru.job4j.model.City;
import java.util.Collection;

public interface CityRepository {
    Collection<City> findAll();
}
