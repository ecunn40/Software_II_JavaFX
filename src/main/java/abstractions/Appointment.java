package abstractions;

import java.sql.Date;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Date appointmentStart;
    private Date appointmentEnd;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int appointmentId, String title, String description, String location, String type, Date appointmentStart, Date appointmentEnd, int customerId, int userId, int contactId) {
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
}
