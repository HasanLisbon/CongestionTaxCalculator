package congestion.calculator.util;

import congestion.calculator.exception.TaxException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date[] getDates(List<String> dates)  {
        return dates.stream()
                .map(dateString->{
                    try {
                        return getDateFromString(dateString);
                    } catch (TaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Date[]::new);
    }

    public static Date getDateFromString(String dateStr) throws TaxException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
           log.error("Invalid Date Format: " + dateStr, e);
            throw new TaxException("Invalid Date Format for input: " + dateStr);
        }
    }
}
