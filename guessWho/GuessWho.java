import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GuessWho here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuessWho extends World implements MyWorld
{
    GameSession gameSession;
    CharacterBox charBox = new CharacterBox(925,460,5,2);
    Character myChar;
    Character yourChar;
    Character guessChar;
    ButtonConfirm buttonGuess= new ButtonConfirm("guess");
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GuessWho(GameSession gameSession)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1536, 864, 1); 
        this.gameSession = gameSession;
        this.myChar = gameSession.getMyChar();
        System.out.println("my character is: " + myChar.getClass().getName());
        this.yourChar = gameSession.getYourChar();
        System.out.println("your (secret) character is: " + yourChar.getClass().getName());
        setup();
    }
    
    private void setup()
    {
        //CharBox setting
        charBox.setMargin(3.5,2.5);
        addObject(charBox,getWidth()/2,600);
        charBox.addAllChars(gameSession.getAllFromPlayList());
        
        //myCharBox Setting
        CharacterBox myCharBox = new CharacterBox(100,100,1,1);
        myCharBox.setImage("yourCharacterCanvas.png");
        myCharBox.setCharScale(1.2);
        myCharBox.setSelectedCharScale(1);
        addObject(myCharBox,1400,600);
        myCharBox.addCharacter(myChar);
        
        addObject(buttonGuess,750,350);
        
    }
    
    public void act()
    {
        guessChar = charBox.getSelectedChar();
        if(guessChar!=null)
            buttonGuess.enableButton();
        else
            buttonGuess.disableButton();   
        
    }
    
    public void buttonClicked(ButtonConfirm button)
    {
        if(button == buttonGuess)
            guessProcessing();
    }
    
    protected void guessProcessing()
    {
        Character selectedChar = charBox.getSelectedChar();
        if(selectedChar != null && selectedChar.getClass() == yourChar.getClass())
        {
            System.out.println("Congratulation! You win");
            charBox.removeSelectedChar();
        }
        else
        {
            System.out.printf("Guess with %s... Wrong guess! Please try again\n", selectedChar.getClass().getName());
            System.out.printf("The right Char should be %s\n", yourChar.getClass().getName());
            charBox.removeSelectedChar();
        }
        
    }
}
