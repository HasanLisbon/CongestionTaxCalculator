package congestion.calculator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CityResponseDto {
    private long id;
    private String name;
    private String code;
    private Set<HolidayResponseDto> holidays;
}
