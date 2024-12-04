package congestion.calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateResponseDto {
    private Long id;
    private String cityCode;
    private String startDate;
    private String endDate;
    private double tax;
}
