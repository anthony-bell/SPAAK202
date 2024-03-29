import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * This class handles the screens for the game result. Such as when a player wins, loses or draws with their opponent.
 * 
 * @SPAAK 
 * 12/4/16
 */
public class ResultScreen extends World implements Observer
{
    
    GameSession gameSession;
    GifImage currentGif;
    DummyImage currentImg;
    /**
     * Constructor for objects of class ResultScreen.
     * 
     */
    public ResultScreen(GameSession gameSession)
    {    
        super(1536, 864, 1); 
        this.gameSession = gameSession;
        prepare();
    }
    
    private void prepare()
    {
        //delete playSet to free memory
        gameSession.getPlaySet().clear();
        
        Character yourChar = gameSession.getYou().getChosenChar();
        yourChar.resizeOnScale(2);
        addObject(yourChar,1200,getHeight()/2);
        
        StringImageFactory a = new StringImageFactory();
        DummyImage text = new DummyImage(a.createImage("Secret Character",60));
        addObject(text,1200,150);
        
        a.setTextColor(Color.BLUE);
        text = new DummyImage(a.createImage("YoUr ReSuLt",100));
        addObject(text,getWidth()/2,80);
        
        EnableButton rtBut = new EnableButton("return","return.png","return.png");
        rtBut.getImage().scale(150,150);
        rtBut.enable();
        rtBut.getImage().scale(150,150);
        rtBut.addObserver(this);
        addObject(rtBut,1200,750);
        
        if(gameSession.getMe().isFinished() && gameSession.getYou().isFinished()){
            drawResult();//Draw Screen
            return;
        }
        
        if(gameSession.getMe().isFinished()){
            winResult();//Me wins
            return;
        }
        
        if(gameSession.getYou().isFinished()){
            loseResult();//Me lose
            return;
        }
        
        disconnectedResult();
 
    }

    
    public void drawResult(){
        StringImageFactory a = new StringImageFactory();
        DummyImage di = new DummyImage(a.createImage("You got it,\nand your friend is as smart as you!!!",40));
        addObject(di,550,250);
        
        di = new DummyImage(new GifImage("draw.gif"));
        addObject(di,550,550);
        
        System.out.println("The game resulted in a draw!");
    }
    
    public void winResult(){
        StringImageFactory a = new StringImageFactory();
        DummyImage di = new DummyImage(a.createImage("You are one step\nor perhaps two steps ahead of your friend!!!",40));
        a.setTextColor(Color.RED);
        addObject(di,550,250);
        
        di = new DummyImage(new GifImage("Congrats-You-Win.gif"));
        addObject(di,550,550);
        
        System.out.println("You win");
    }
    
    public void loseResult(){
        StringImageFactory a = new StringImageFactory();
        a.setTextColor(Color.RED);
        String sss = "Your friend correctly guess your character before you!!!";
        sss += "\nAsk him for his strategy if he has one";
        DummyImage di = new DummyImage(a.createImage(sss,40));
        addObject(di,550,250);
        
        
        di = new DummyImage(new GifImage("you-lose.gif"));
        addObject(di,550,550);
        System.out.println("You Lose");
    }
    
    public void disconnectedResult(){
        StringImageFactory a = new StringImageFactory();
        a.setTextColor(Color.CYAN);
        DummyImage di = new DummyImage(a.createImage("Your friend is disconected,\nask if he has just gave up and fleed away!!!",40));
        addObject(di,550,250);
        
        di = new DummyImage(new GreenfootImage("disconnect.png"));        
        addObject(di, 550,550);
        System.out.println("Player was disconnected!!");
    }
    
    public void update(Observable o, Object arg)
    {
        PlayerAdapter pa = new PlayerAdapter();
        pa.delete(gameSession.getSessionID());
        Greenfoot.setWorld(new WelcomeScreen());
    }
}
