package congestion.calculator.service;

import congestion.calculator.entity.*;
import congestion.calculator.exception.TaxException;
import congestion.calculator.model.Vehicle;
import congestion.calculator.repositories.CityRepository;
import congestion.calculator.repositories.HolidayRepository;
import congestion.calculator.repositories.RateRepository;
import congestion.calculator.util.VehicleVerification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static congestion.calculator.util.DateUtil.getDates;

@Service
@Slf4j
public class CongestionTaxService {

    public static final long DAYS_BEFORE_HOLIDAY=2;
    private static Map<String, Integer> tollFreeVehicles = new HashMap<>();
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private RateRepository rateRepository;

    static {
        tollFreeVehicles.put("Motorcycle", 0);
        tollFreeVehicles.put("Bus", 1);
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);

    }

    public TaxResponse calculateTax(TaxRequest taxRequest) throws TaxException {

        VehicleVerification vehicleFactory = new VehicleVerification();
        Vehicle vehicle = vehicleFactory.getVehicle(taxRequest.getVehicle());
        if (vehicle == null) {
            log.warn("Invalid Vehicle Type");
            throw new TaxException("Invalid Vehicle Type");
        }

        Date[] dates = getDates(taxRequest.getTime());

        if (dates.length == 0) {
            log.warn("Dates are Empty");
            throw new TaxException("Dates are Empty");
        }

        TaxResponse response = new TaxResponse();
        double tax = getTax(vehicle, dates, taxRequest.getCity());
        response.setTax(tax);

        return response;
    }

    public int getTax(Vehicle vehicle, Date[] dates, String cityName) throws TaxException {
        Date intervalStart = dates[0];
        int totalFee = 0;
        City city = cityRepository.findOneByCode(cityName);

        if (city == null) {
            log.warn("Invalid City");
            throw new TaxException("Invalid City");
        }

        for (int i = 0; i < dates.length ; i++) {
            Date date = dates[i];
            double nextFee = getTollFee(date, vehicle, city);
            double tempFee = getTollFee(intervalStart, vehicle, city);

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies/1000/60;

            if (minutes <= 60)
            {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            }
            else
            {
                totalFee += nextFee;
            }
        }

        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }

    private Optional<Double> getRateByTime(LocalDateTime localDate, Rate rate) {
        LocalDateTime startDate = convertToLocalDateTimeViaMilisecond(rate.getStartDate());
        LocalDateTime endDate = convertToLocalDateTimeViaMilisecond(rate.getEndDate());

        LocalDateTime startTime = localDate.withHour(startDate.getHour()).withMinute(startDate.getMinute());
        LocalDateTime endTime = localDate.withHour(endDate.getHour()).withMinute(endDate.getMinute());

        if (!localDate.isBefore(startTime) && localDate.isBefore(endTime)) {
            return Optional.of(rate.getTax());
        }

        return Optional.empty();
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return tollFreeVehicles.containsKey(vehicleType);
    }

    public double getTollFee(Date date, Vehicle vehicle, City city)
    {
        LocalDateTime localDate = convertToLocalDateTimeViaMilisecond(date);

        if (isTollFreeDate(date, city) || isTollFreeVehicle(vehicle)) {
            return 0.0d; // Early exit if toll is free
        }

        List<Rate> rates = rateRepository.findByCity(city);

        Optional<Double> taxRate = rates.stream()
                .map(rate -> getRateByTime(localDate, rate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return taxRate.orElse(0.0);
    }



    private Boolean isTollFreeDate(Date date, City city)
    {
        LocalDateTime localDate = convertToLocalDateTimeViaMilisecond(date);

        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return true;
        }

        if (localDate.getYear() == 2013) {
            List<Holiday> holidays = holidayRepository.findByCity(city);

            return holidays.stream().anyMatch(holiday -> {
                LocalDateTime startDate = convertToLocalDateTimeViaMilisecond(holiday.getDate());
                LocalDateTime beforeDate = startDate.minusDays(DAYS_BEFORE_HOLIDAY);

                return !localDate.isBefore(beforeDate) && localDate.isBefore(startDate);
            });
        }

        return false;
    }


    public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
