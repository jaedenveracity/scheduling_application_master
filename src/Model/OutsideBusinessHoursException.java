package Model;

public class OutsideBusinessHoursException extends Exception{

    public OutsideBusinessHoursException()
    {
        super();
    }

    public OutsideBusinessHoursException(Exception e)
    {
        super(e);
    }

    public OutsideBusinessHoursException(String message)
    {
        super(message);
    }

}
