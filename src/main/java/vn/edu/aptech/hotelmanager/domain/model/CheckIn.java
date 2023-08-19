package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class CheckIn {
    private long id;
    private long customerId;
    private long roomId;
    private Date checkInAt;
    private Date checkOutAt;
    private int status;

    public long totalHours(){
        if (checkOutAt == null || checkInAt == null) {
            return 1;
        }
        //milliseconds
        long different = checkOutAt.getTime() - checkInAt.getTime();

        System.out.println("startDate : " + checkInAt);
        System.out.println("endDate : "+ checkOutAt);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        //long elapsedDays = different / daysInMilli;
        //different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d hours, %d minutes, %d seconds%n",
                elapsedHours, elapsedMinutes, elapsedSeconds);

        if (elapsedHours <= 0) {
            return 1;
        }
        return elapsedHours;
    }
}
