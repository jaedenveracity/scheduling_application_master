package Model;

public class OverlappingAppointmentException extends Exception {

    public OverlappingAppointmentException()
    {
        super();
    }

    public OverlappingAppointmentException(Exception e)
    {
        super(e);
    }

    public OverlappingAppointmentException(String message)
    {
        super(message);
    }
}
