package congestion.calculator;

import congestion.calculator.exception.TaxException;
import congestion.calculator.model.Vehicle;
import congestion.calculator.service.CongestionTaxService;
import congestion.calculator.util.VehicleVerification;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorApplicationTests {


	@Autowired
	@InjectMocks
	private CongestionTaxService congestionTaxService;


	@Test
	public void testCarTax() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 15:31:59"), getDateFromString("25-11-2013 16:10:59") };
		assertEquals(Double.valueOf(18), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testSingleChargeRule() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 08:10:59"), getDateFromString("25-11-2013 09:08:59") };
		assertEquals(Double.valueOf(13), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testNoneSingleChargeRule() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 08:10:59"), getDateFromString("25-11-2013 10:20:59") };
		assertEquals(Double.valueOf(21), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testHolidayTax() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("28-03-2013 15:31:59"), getDateFromString("28-03-2013 16:10:59") };
		assertEquals(Double.valueOf(0), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testJulyTax() throws TaxException{
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("28-07-2013 15:31:59"), getDateFromString("28-07-2013 16:10:59") };
		assertEquals(Double.valueOf(0), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testHolidayTaxWithBeforeDay() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("27-03-2013 15:31:59"), getDateFromString("27-03-2013 16:10:59") };
		assertEquals(Double.valueOf(0), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testExemptedVehicle() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Military");
		Date[] dates = { getDateFromString("27-02-2013 15:31:59"), getDateFromString("27-02-2013 16:10:59") };
		assertEquals(Double.valueOf(0), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testMaximumAmount() throws TaxException {
		VehicleVerification vehicleVerification = new VehicleVerification();
		Vehicle vehicle = vehicleVerification.getVehicle("Car");
		Date[] dates = { getDateFromString("12-02-2013 06:10:59"), getDateFromString("12-02-2013 06:30:59"),
				getDateFromString("12-02-2013 07:05:59"), getDateFromString("12-02-2013 07:30:59"),
				getDateFromString("12-02-2013 08:20:59"), getDateFromString("12-02-2013 08:40:59"),
				getDateFromString("12-02-2013 09:30:59"), getDateFromString("12-02-2013 15:10:59"),
				getDateFromString("12-02-2013 15:40:59"), getDateFromString("12-02-2013 16:30:59"),
				getDateFromString("12-02-2013 17:30:59"), getDateFromString("12-02-2013 18:10:59") };
		assertEquals(Double.valueOf(60), congestionTaxService.getTax(vehicle, dates, "GB"));
	}

	public Date getDateFromString(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		String dateInString = dateStr;
		Date date;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

}
