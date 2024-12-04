package congestion.calculator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayResponseDto {
    private Long id;               // ID of the holiday
    private String city;           // Formatted holiday date
    private String date;    // Description of the holiday (if applicable)

}
