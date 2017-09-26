
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
/**
 *
 * @author KK
 */
public class GameState extends Model {
    public enum State {
        PAUSED, STARTED, NOTSTARTED, INPROGRESS
    }
    private int FPS;
    private int scrollSpeed;
    private KeyStroke up;
    private KeyStroke left;
    private KeyStroke down;
    private KeyStroke right;
    private State state;
    private float playerX;
    private float playerY;
    private List<Block> blocks;
    private int width;
    private int height;
    public boolean gup;
    public boolean gdown;
    public boolean gleft;
    public boolean gright;
    
    public void setWidth(int width) {
        this.width = width;
        this.notifyObservers();
    }

    public void setHeight(int height) {
        this.height = height;
        this.notifyObservers();
    }
   

    public int getWidth() {
        
        return width;
        
    }

    public int getHeight() {
        return height;
    }

    public void setPlayerX(float playerX) {
       
        this.playerX = playerX;
         this.notifyObservers();
    }

    public void setPlayerY(float playerY) {
        
        this.playerY = playerY;
        this.notifyObservers();
    }

    public void addBlocks(Block block) {
        //System.out.print(block);
        this.blocks.add(block);
        this.notifyObservers();
        
    }

    public int getFPS() {
        return FPS;
    }

    public float getPlayerX() {
        return playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
    
    public GameState(){
    
        FPS = 60;
        scrollSpeed = 1;
        up = KeyStroke.getKeyStroke('w',0);
        down = KeyStroke.getKeyStroke('s',0);
        left = KeyStroke.getKeyStroke('a',0);
        right = KeyStroke.getKeyStroke('d',0);
        state = State.NOTSTARTED;
        playerX = 0;
        playerY = 0;
        blocks = new ArrayList<Block>();
        gup = false;
        gdown = false;
        gleft = false;
        gright = false;
    }

    public int getScrollSpeed() {
        return scrollSpeed;
    }

    public KeyStroke getUp() {
        return up;
    }

    public KeyStroke getLeft() {
        return left;
    }

    public KeyStroke getDown() {
        return down;
    }

    public KeyStroke getRight() {
        return right;
    }
    
    public State getState(){
        return state;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
          this.notifyObservers();
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
          this.notifyObservers();
    }

    public void setUp(KeyStroke up) {
      
        this.up = up;
          this.notifyObservers();
    }

    public void setLeft(KeyStroke left) {
        
        this.left = left;
        this.notifyObservers();
    }

    public void setDown(KeyStroke down) {
      
        this.down = down;
          this.notifyObservers();
    }

    public void setRight(KeyStroke right) {
        
        this.right = right;
        this.notifyObservers();
    }

    public void setState(State state) {
        
        this.state = state;
        this.notifyObservers();
    }

   
    
           
}
