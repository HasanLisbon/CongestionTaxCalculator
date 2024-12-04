package congestion.calculator.service.rate;

import congestion.calculator.dto.RateDto;
import congestion.calculator.dto.RateResponseDto;

import java.util.List;

public interface RateService {
    RateResponseDto createRate(RateDto rateDto);
    RateResponseDto getRate(long id);
    RateResponseDto updateRate(long id, RateDto rateDto);
    void deleteRate(long id);
    List<RateResponseDto> getAllRate();
}
