package congestion.calculator.util;

import congestion.calculator.dto.RateResponseDto;
import congestion.calculator.entity.Rate;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class RateMapper {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static RateResponseDto toRateResponseDto(Rate rate) {
        if (rate == null) {
            return null;
        }

        RateResponseDto dto = new RateResponseDto();
        dto.setId(rate.getId());
        dto.setCityCode(rate.getCity().getCode());
        dto.setStartDate(dateFormat.format(rate.getStartDate()));
        dto.setEndDate(dateFormat.format(rate.getEndDate()));
        dto.setTax(rate.getTax());

        return dto;
    }

    public static List<RateResponseDto> toRateResponseDtoList(List<Rate> rates) {
        return rates.stream()
                .map(RateMapper::toRateResponseDto)
                .collect(Collectors.toList());
    }
}
