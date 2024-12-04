package congestion.calculator.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TaxResponse {

	@JsonProperty(ATTR_TAX)
	public double tax;
	public static final String ATTR_TAX = "tax";

	@JsonProperty(ATTR_CURRENCY)
	public String currency = "SEK";
	public static final String ATTR_CURRENCY = "currency";

}
