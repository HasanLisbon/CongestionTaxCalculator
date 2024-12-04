package congestion.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RateDto {
    private String cityCode;
    private String startDate;
    private String endDate;
    private double tax;
}
