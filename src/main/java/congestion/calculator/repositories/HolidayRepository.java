package congestion.calculator.repositories;

import congestion.calculator.entity.City;
import congestion.calculator.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
	
	List<Holiday> findByCity(City city);

}
