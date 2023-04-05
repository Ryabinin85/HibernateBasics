package pro.sky.hibernatebasics.dao.citydao;

import pro.sky.hibernatebasics.model.City;

public interface CityDao {

    City getCityById(Long id);

    City getCityByEmployeeId(Long id);
}
