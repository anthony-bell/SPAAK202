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
    MyObservable o = new MyObservable();
    public FilteringState(GuessWho world,GameSession gameSession)
    {
        this.world = world;
        this.gameSession = gameSession;
        playSet = gameSession.getPlaySet();
        addObserver((Observer)world.getState("scoringState"));
    }
    
    public void stateRun(Object arg)
    {
        //System.out.println(option + " : " +subOption);
        String filterKey = option;
        String filterValue = gameSession.getYourChar().getPropertyValue(filterKey);
        
        Map<String,String> filterSession = new HashMap<String,String>();
        filterSession.put("operationType","filter");
        
        int tileCount;
        
        if(filterValue.equals(subOption))
        {
            //System.out.println("Good Guess!!!");
            tileCount = correct();
            filterSession.put("correctioness","correct");
        }
        else
        {
            //System.out.println("Too Bad!!!");
            tileCount = incorrect();    
            filterSession.put("correctioness","incorrect");
        }

        filterSession.put("tileCount",Integer.toString(tileCount));
        
        o.setCHANGED();
        o.notifyObservers(filterSession);
        o.clearCHANGED();
        
        world.setState("waitingState"); 
    }
    
    public int correct()
    {
        Set<Character> rmSet = new HashSet<Character>();
        for(Character c : playSet)
        {
            if(c.getPropertyValue(option) != subOption)
                rmSet.add(c);
        }    
        return removeSet(rmSet);
    }
    
    public int incorrect()
    {
        Set<Character> rmSet = new HashSet<Character>();
        for(Character c : playSet)
        {
            if(c.getPropertyValue(option) == subOption)
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
        if (arg instanceof LabelButton)
        {
            LabelButton but = (LabelButton)arg;
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
        PropertyInfo propertyInfo = gameSession.getPropertyInfo();
        if(propertyInfo.getKeys().contains(str))
            return "option";
        return "subOption";
        
    }
   
    public void addObserver(Observer observer)
    {
        o.addObserver(observer);
    }
}
