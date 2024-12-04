package congestion.calculator.util;

import congestion.calculator.exception.TaxException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

@Slf4j
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date[] getDates(List<String> dates)  {
        // Use streams to convert the list of strings to an array of Date objects
        return dates.stream()
                .map(dateString->{
                    try {
                        return getDateFromString(dateString); // This can throw TaxException
                    } catch (TaxException e) {
                        // Wrap TaxException in RuntimeException
                        throw new RuntimeException(e);  // Handle it or propagate it
                    }
                })
                .toArray(Date[]::new);
    }

    private static Date getDateFromString(String dateStr) throws TaxException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
           log.error("Invalid Date Format: " + dateStr, e);  // Log the exception for easier debugging
            throw new TaxException("Invalid Date Format for input: " + dateStr);
        }
    }
}
