package congestion.calculator.service.city;

import congestion.calculator.dto.CityDto;
import congestion.calculator.dto.CityResponseDto;

import java.util.List;

public interface CityService {
    CityResponseDto createCity(CityDto cityDto);
    CityResponseDto getCity(long id);
    CityResponseDto updateCity(long id, CityDto cityDto);
    void deleteCity(long id);
    List<CityResponseDto> getAllCity();
}
