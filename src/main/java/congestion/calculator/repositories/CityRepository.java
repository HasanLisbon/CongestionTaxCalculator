package congestion.calculator.repositories;

import congestion.calculator.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	City findByCityId(String code);
}
