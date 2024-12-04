package congestion.calculator.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TaxRequest {

	@JsonProperty(ATTR_CITY)
	public String city;
	public static final String ATTR_CITY = "city";

	@JsonProperty(ATTR_VEHICLE)
	public String vehicle;
	public static final String ATTR_VEHICLE = "vehicle";

	@JsonProperty(ATTR_DATES)
	public List<String> time;
	public static final String ATTR_DATES = "time";

}
