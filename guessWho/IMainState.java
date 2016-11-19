import greenfoot.*;
//import java.util.Observer;
//import java.util.Observable;
/**
 * Write a description of class IGuessWhoState here.
 * 
 * @author SPAAK 
 * 
 */
public class IMainState extends IGameState
{
    PressHandler successor;
    World world;
    int stateTime;
    public IMainState(World world)
    {
        this.world = world;
        stateTime = 5;
    }
    
    
    public void setSuccessor(PressHandler successor)
    {
        if(this.successor != null)
            successor.setSuccessor(this.successor);

        this.successor = successor;
    }
    
    public void pressHandle(int x, int y)
    {
        if(successor != null)
            successor.pressHandle(x,y);
    }
    
    public void enter()
    {
        
        
    }
    
    public void stateRun()
    {
        if(Greenfoot.mousePressed(null))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            int x = mouse.getX();
            int y = mouse.getY();
            pressHandle(x,y);
        }
    }
    
    public void exit()
    {
        //world.setState("guessWhoState");
    }
    
    public void secondUpdate()
    {
        stateTime -= 1;
        System.out.println(stateTime);
        if(stateTime == 0)
        {
            stateTime = 5;
            exit();
        }
    }
    
}