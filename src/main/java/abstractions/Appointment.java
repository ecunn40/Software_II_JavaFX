package abstractions;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Blueprint for an appointment
 */
public class Appointment {
    /**
     * Date time format variable
     */
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Appointment constructor
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param appointmentStart
     * @param appointmentEnd
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    /**
     * @return appointment start as a string
     */
    public String getAppointmentStart() {
        return dtf.format(appointmentStart);
    }

    /**
     * @return appointment start as a LocalDateTime
     */
    public LocalDateTime getAppointmentTStart() { return appointmentStart; }

    public String getAppointmentEnd() {
        return dtf.format(appointmentEnd);
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }
}
