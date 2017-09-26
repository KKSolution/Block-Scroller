


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.lang.System.console;


public class GamePanel extends JPanel implements Observer {
   
    private Timer timer;
    private GameState state;
    private float scroll;
    
    public GamePanel(GameState state) {
        super();

        // Using Mouse Listeners to handle mouse events
        this.state = state;
      this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_W,0, false)), "upp" );
      this.getActionMap().put("upp", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gup = true;
        
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_A,0, false)), "leftp" );
      this.getActionMap().put("leftp", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gleft = true;
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_D,0, false)), "rightp" );
      this.getActionMap().put("rightp", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gright = true;
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_S,0, false)), "downp" );
      this.getActionMap().put("downp", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gdown = true;
          }
      });
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_W,0, true)), "upr" );
      this.getActionMap().put("upr", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gup = false;
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_A,0, true)), "leftr" );
      this.getActionMap().put("leftr", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gleft = false;
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_D,0, true)), "rightr" );
      this.getActionMap().put("rightr", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gright = false;
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put((KeyStroke.getKeyStroke(KeyEvent.VK_S,0, true)), "downr" );
      this.getActionMap().put("downr", new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              GamePanel.this.state.gdown = false;
          }
      });

        // Add a Timer to handle animation.
		// Your Timer should use this.handleAnimation() and this.repaint().
        scroll = 0;
        
        timer = new Timer(1000/this.state.getFPS(), new ActionListener(){
           public void actionPerformed(ActionEvent evt){
               if (GamePanel.this.state.getState() == GameState.State.STARTED){
                
                GamePanel.this.handleAnimation();
                GamePanel.this.repaint();
                GamePanel.this.CheckGame();
               }
           } 
        });
        timer.start();
    }
    public void startGame(){
        timer.start(); 
    }
    public void pauseGame(){
        timer.stop();
    }
    @Override
    protected void paintComponent(Graphics g) {
        // dBuff and gBuff are used for double buffering
        Image dBuff = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_3BYTE_BGR);
        Graphics gBuff = dBuff.getGraphics();
        gBuff.setClip(0, 0, this.getWidth(), this.getHeight());
      
        gBuff.setColor(Color.CYAN);
        gBuff.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (this.state.getState() != GameState.State.NOTSTARTED){
            for (Block block : GamePanel.this.state.getBlocks()){
                 paintBlock(gBuff, block);
             }
            paintPlayer(gBuff);
        }
        g.drawImage(dBuff, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void handleAnimation() {
       scroll += (float)state.getScrollSpeed() / state.getFPS();
          if (this.state.gup){
           this.state.setPlayerY(Math.max(0,this.state.getPlayerY() - (this.state.getHeight()/(float)this.state.getFPS())));
       }
        if (this.state.gdown){
           this.state.setPlayerY(Math.min(this.state.getHeight()-1, this.state.getPlayerY() + (this.state.getHeight()/(float)this.state.getFPS())));
       }
         if (this.state.gleft){
           this.state.setPlayerX(Math.max(scroll,this.state.getPlayerX() - (4* this.state.getScrollSpeed()/(float)this.state.getFPS())));
       }
          if (this.state.gright){
           this.state.setPlayerX(Math.min(scroll + 19,this.state.getPlayerX() + (4 * this.state.getScrollSpeed()/(float)this.state.getFPS())));
       }
    }
    private void paintPlayer(Graphics g){
         Graphics2D g2 = (Graphics2D) g;
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g2.setComposite(comp);
        
        g2.setColor(Color.RED);
        g2.fillRect(
                (int)(this.state.getPlayerX() * this.getWidth() - (scroll * this.getWidth())) / 20, 
                (int) (this.state.getPlayerY() * this.getHeight() / GamePanel.this.state.getHeight()), 
                this.getWidth() / 20, //Only showing 20 blocks at max
                this.getHeight()/this.state.getHeight()) ;
        //System.out.println(this.state.getPlayerY());
        
    }
    private void paintBlock(Graphics g, Block block){
        Graphics2D g2 = (Graphics2D) g;
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g2.setComposite(comp);
        int width = block.getBotX() - block.getTopX() + 1 ;
        int height = block.getBotY() - block.getTopY();
        g2.setColor(Color.BLACK);
        g2.fillRect(
                (int)((block.getTopX()) * this.getWidth() - (scroll * this.getWidth())) / 20, 
                block.getTopY() * this.getHeight() / GamePanel.this.state.getHeight(), 
                width * this.getWidth() / 20, //Only showing 20 blocks at max
                this.getHeight()* height / GamePanel.this.state.getHeight());
       // System.out.println(scroll);
       // System.out.println(block.getTopY());
    }
    public void update(Object observable){
        
    }
    
    private void GameOver(){
        this.state.setState(GameState.State.NOTSTARTED);
                       this.state.setPlayerX(0);
                       this.state.setPlayerY(0);
                       this.state.gup= false;
                       this.state.gdown =false;
                       this.state.gleft = false;
                       this.state.gright = false;
                       scroll = 0;
    }
    private void CheckGame(){
     
          //check for ints
          for (Block block : GamePanel.this.state.getBlocks()){
                 if (!((this.state.getPlayerX() + 1 < block.getTopX()) ||
                         ((float)block.getBotX() + 1< this.state.getPlayerX()) ||
                         (this.state.getPlayerY() + 1 < block.getTopY()) || 
                         ((float)block.getBotY() < this.state.getPlayerY())))
                 {
                       
                       JOptionPane.showMessageDialog(null, "YOU CRASHED", "YOU LOST!", 0);
                       //System.out.print(this.state.getPlayerY());
                       System.out.print(block.getBotY());
                       this.GameOver();
                                 return;

     
                 }
             }
          if (this.state.getPlayerX() < scroll){
              System.out.println(this.state.getPlayerX());
               JOptionPane.showMessageDialog(null, "YOU FELL BEHIND", "YOU LOST!", 0);
               this.GameOver();
          }
          if (this.state.getPlayerX()  > this.state.getWidth() + 5){
                     JOptionPane.showMessageDialog(null, "YOU WON!", "WINNNER", 0);
               this.GameOver();
          }
    }
    
}