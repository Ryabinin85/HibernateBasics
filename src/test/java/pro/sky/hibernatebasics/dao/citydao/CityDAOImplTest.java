package pro.sky.hibernatebasics.dao.citydao;

import org.junit.jupiter.api.Test;
import pro.sky.hibernatebasics.exceptions.CustomException;
import pro.sky.hibernatebasics.model.City;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityDAOImplTest {

    private final CityDAOImpl out = new CityDAOImpl();
    private final Long ID = 1L;
    private final City MOSCOW = new City(1L, "Moscow");
    private final Long WRONG_ID = 100000000L;

    @Test
    void ShouldFindCityById() {
        assertEquals(out.getCityById(ID), MOSCOW);
    }

    @Test
    void ShouldThrowExceptionWhenNotFoundCityById() {
        assertThrows(CustomException.class, () -> out.getCityById(WRONG_ID));
    }
}