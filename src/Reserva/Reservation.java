
package Reserva;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Reservation(Item item, LocalDateTime startTime, LocalDateTime endTime) {

    private List<Item> reservations;

    public boolean conflictsWith(LocalDateTime newStartTime, LocalDateTime newEndTime) {
        // Check if the new reservation conflicts with this reservation
        return (newStartTime.isBefore(endTime) && newEndTime.isAfter(startTime)) ||
                (newStartTime.isEqual(endTime) || newEndTime.isEqual(startTime));
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customerName='" + customerName + '\'' +
                ", startTime=" + startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                ", endTime=" + endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}