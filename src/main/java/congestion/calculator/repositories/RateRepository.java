package congestion.calculator.repositories;

import congestion.calculator.entity.City;
import congestion.calculator.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
	
	List<Rate> findByCity(City city);

}
