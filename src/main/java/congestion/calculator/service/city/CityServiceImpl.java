package congestion.calculator.service.city;

import congestion.calculator.dto.CityDto;
import congestion.calculator.dto.CityResponseDto;
import congestion.calculator.entity.City;
import congestion.calculator.repositories.CityRepository;
import congestion.calculator.repositories.HolidayRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static congestion.calculator.util.CityMapper.*;

@Service
public class CityServiceImpl implements CityService{
    @Autowired
    CityRepository cityRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Override
    public CityResponseDto createCity(CityDto cityDto) {
        City city = new City();
        city.setCode(cityDto.getCode());
        city.setName(cityDto.getName());
        city.setHolidays(new HashSet<>(holidayRepository.findAll()));
        return toCityResponseDto(cityRepository.save(city));
    }

    @Override
    public CityResponseDto getCity(long id) {
        return toCityResponseDto(cityRepository.findById(id).orElseThrow(()-> new NoSuchElementException("City could not be found")));
    }

    @Transactional
    @Override
    public CityResponseDto updateCity(long id, CityDto cityDto) {
        City city = cityRepository.findById(id).orElseThrow(()-> new NoSuchElementException("City could not be found"));
        city.setName(cityDto.getName());
        city.setCode(cityDto.getCode());
        return toCityResponseDto(cityRepository.save(city));
    }

    @Override
    public void deleteCity(long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public List<CityResponseDto> getAllCity() {
        return toCityResponseDtos(cityRepository.findAll());
    }
}
