/**
 * Write a description of class Command here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Command  
{
    public void setReceiver(Receiver receiver);
    public void execute(Object arg);
}
