package congestion.calculator.service.rate;

import congestion.calculator.dto.RateDto;
import congestion.calculator.dto.RateResponseDto;
import congestion.calculator.entity.City;
import congestion.calculator.entity.Rate;
import congestion.calculator.exception.ResourceNotFoundException;
import congestion.calculator.exception.TaxException;
import congestion.calculator.repositories.CityRepository;
import congestion.calculator.repositories.RateRepository;
import congestion.calculator.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static congestion.calculator.util.DateUtil.getDateFromString;
import static congestion.calculator.util.RateMapper.toRateResponseDto;
import static congestion.calculator.util.RateMapper.toRateResponseDtoList;

@Service
@Slf4j
public class RateServiceImpl implements RateService {
    @Autowired
    RateRepository rateRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public RateResponseDto createRate(RateDto rateDto) {
        Rate rate = new Rate();

        try {
            City city = cityRepository.findOneByCode(rateDto.getCityCode());
            if (city == null) {
                throw new RuntimeException("City could not be found");
            }
            rate.setCity(city);
            rate.setStartDate(getDateFromString(rateDto.getStartDate()));
            rate.setEndDate(getDateFromString(rateDto.getEndDate()));
            rate.setTax(rateDto.getTax());
        } catch (TaxException e) {
            log.error(e.getMessage(), e);
        }
        return toRateResponseDto(rateRepository.save(rate));
    }

    @Override
    public RateResponseDto getRate(long id) {
        return toRateResponseDto(rateRepository.getReferenceById(id));
    }


    @Transactional
    @Override
    public RateResponseDto updateRate(long id, RateDto rateDto) {
        Rate updatedRate = null;
        try {
            Rate rate = rateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rate could not be found"));
            City city = cityRepository.findOneByCode(rateDto.getCityCode());
            if (city == null) {
                throw new RuntimeException("City could not be found");
            }

            rate.setCity(city);
            rate.setStartDate(getDateFromString(rateDto.getStartDate()));
            rate.setEndDate(getDateFromString(rateDto.getEndDate()));
            rate.setTax(rateDto.getTax());
            updatedRate = rateRepository.save(rate);

        } catch (TaxException e) {
            log.error(e.getMessage(), e);
        }
        return toRateResponseDto(updatedRate);
    }

    @Override
    public void deleteRate(long id) {
        rateRepository.deleteById(id);
    }

    @Override
    public List<RateResponseDto> getAllRate() {
        return toRateResponseDtoList(rateRepository.findAll());
    }
}
