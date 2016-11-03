import greenfoot.World;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;
import java.util.Observable;

public class FilteringState extends SimpleGameState implements Observer
{
    GuessWho world;
    Set<Character> playSet;
    GameSession gameSession;
    String option;
    String subOption;
    MyObservble o = new MyObservable();
    public FilteringState(GuessWho world,GameSession gameSession)
    {
        this.world = world;
        this.gameSession = gameSession;
        playSet = gameSession.getPlaySet();
    }
    
    public void stateRun(Object arg)
    {
        System.out.println(option + " : " +subOption);
        
        String filterKey = option;
        String filterValue = gameSession.getMyChar().getSubOpt(filterKey);
        
        int tileCount;
        
        if(filterValue.equals(subOption))
        {
            System.out.println("Good Guess!!!");
            tileCount = correct();
        }
        else
        {
            System.out.println("Too Bad!!!");
            tileCount = incorrect();    
        }
        
        o.setCHANGED();
        o.notifyObserver();
        o.clearCHANGED();
        
        world.setState("waitingState"); 
    }
    
    public int correct()
    {
        Set<Character> rmSet = new HashSet<Character>();
        for(Character c : playSet)
        {
            if(c.getSubOpt(option) != subOption)
                rmSet.add(c);
        }    
        return removeSet(rmSet);
    }
    
    public int incorrect()
    {
        Set<Character> rmSet = new HashSet<Character>();
        for(Character c : playSet)
        {
            if(c.getSubOpt(option) == subOption)
                rmSet.add(c);
        } 
        return removeSet(rmSet);
    }
    
    private int removeSet(Set<Character> rmSet)
    {
        for(Character c : rmSet)
        {
            world.removeObject(c);
            playSet.remove(c);
        }
        return rmSet.size();
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg != null)
        {
            StringButton but = (StringButton)arg;
            String label = but.getLabel();
            if(classify(label).equals("option"))
            {
                this.option = label;
                return;
            }
            this.subOption = label;
        }
    }
    
    public String classify(String str)
    {
        OptionInfo optionInfo = gameSession.getOptionInfo();
        if(optionInfo.getOptions().contains(str))
            return "option";
        return "subOption";
        
    }
    
    
    public void addObserver(Observer observer)
    {
        o.addObserver(observer);
    }
}
