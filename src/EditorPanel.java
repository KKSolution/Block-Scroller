


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Stack;


import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.lang.Math.ceil;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EditorPanel extends JPanel implements Observer {
   
    private Timer timer;
    private GameState state;
    private float scroll;
    public Stack<Block> blocks = new Stack<Block>();
    public Stack<Block> redo = new Stack<Block>();
    public int levelwidth;
    public int levelheight;
    public int clickX;
    public int clickY;
      public boolean create;
      public Block selectedBlock;
      public Block copyBlock;
      public boolean selectedEmptyX = true;
    public EditorPanel(GameState state) {
      super();
      this.create = true;
      this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                            java.awt.event.InputEvent.CTRL_DOWN_MASK),
                    "cut");
        this.getActionMap().put("cut",
                     new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              //GamePanel.this.state.gup = true;
           
                 copyBlock = selectedBlock;
             
          }
      });
       this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                            java.awt.event.InputEvent.CTRL_DOWN_MASK),
                    "paste");
        this.getActionMap().put("paste",
                     new AbstractAction(){
          @Override
          public void actionPerformed(ActionEvent e){
              //GamePanel.this.state.gup = true;
                  int distanceX = copyBlock.getBotX() - copyBlock.getTopX();
                   int distanceY = copyBlock.getBotY() - copyBlock.getTopY();
                  
                    
                   
               int topX = levelwidth * MouseInfo.getPointerInfo().getLocation().x/EditorPanel.this.getWidth();
               int topY = levelheight * MouseInfo.getPointerInfo().getLocation().y/EditorPanel.this.getHeight();
               
               blocks.push(new Block(topX , topY - 2, topX + distanceX, topY - 2+ distanceY));
             
          }
      });  
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!create){
                    if (selectedBlock != null){
                     selectedBlock.selected = false;
                    }
                for (Block block : blocks){
                      int X = levelwidth * e.getX()/EditorPanel.this.getWidth();
                      int Y = levelheight * e.getY()/EditorPanel.this.getHeight() + 1 ;
                      if ((block.getTopX() <= X) && (X <= block.getBotX()) ){
                          if ((block.getTopY() <= Y) && (Y <= block.getBotY()) ){
                              block.selected = true;
                              selectedBlock = block;
                             
                          }
                      } 
                     
                  }       
                }
                  
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                clickX = e.getX();
                clickY = e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!create){
                    return;
                }
               int finalX = e.getX();
               int finalY = e.getY();
               
               int topX = levelwidth * clickX/EditorPanel.this.getWidth();
               int topY = levelheight * clickY/EditorPanel.this.getHeight();
               
               int botX = levelwidth * finalX/EditorPanel.this.getWidth();
               int botY = levelheight * finalY/EditorPanel.this.getHeight() + 1;
                     
                
               blocks.push(new Block(topX, topY, botX, botY));
               redo.clear();
              
            }
        });
        // Using Mouse Listeners to handle mouse events
      this.state = state;
      levelwidth = 20;
      levelheight = 10;
     

        // Add a Timer to handle animation.
		// Your Timer should use this.handleAnimation() and this.repaint().
        scroll = 0;
        
        timer = new Timer(1000/60, new ActionListener(){
           public void actionPerformed(ActionEvent evt){
               
                
                
                EditorPanel.this.repaint();
                //System.out.println(EditorPanel.this.getHeight());
               
           } 
        });
        timer.start();
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        // dBuff and gBuff are used for double buffering
        Image dBuff = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_3BYTE_BGR);
        Graphics gBuff = dBuff.getGraphics();
        gBuff.setClip(0, 0, this.getWidth(), this.getHeight());
        
        gBuff.setColor(Color.CYAN);
        gBuff.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawGrid(gBuff);
        for (Block block : blocks){
            paintBlock(gBuff, block);
        }
            
      
        g.drawImage(dBuff, 0, 0, this.getWidth(), this.getHeight(), null);
    }
    
    private void drawGrid(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g2.setComposite(comp);
        
        g2.setColor(Color.BLACK) ;
        int tmpwidth = this.getWidth();
        for (double i = 0; i < tmpwidth; i = i + (tmpwidth/this.levelwidth)){
            g2.drawLine((int)ceil(i), 0, (int)ceil(i), this.getHeight());
        }
        
        int tmpheight = this.getHeight();
        for (int i = 0; i < tmpheight; i = i + (tmpheight/this.levelheight)){
            g2.drawLine(0, (int)ceil(i), this.getWidth(), (int)ceil(i));
        }
    }
    
 
    private void paintBlock(Graphics g, Block block){
        
        Graphics2D g2 = (Graphics2D) g;
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g2.setComposite(comp);
        int width = block.getBotX() - block.getTopX() + 1 ;
        int height = block.getBotY() - block.getTopY();
        g2.setColor(Color.RED);
        
  g2.fillRect(
                (int)((block.getTopX()) * this.getWidth() - (scroll * this.getWidth())) / levelwidth, 
                block.getTopY() * this.getHeight() / levelheight, 
                width * this.getWidth() / levelwidth, //Only showing 20 blocks at max
                this.getHeight()* height / levelheight);
        if (block.selected){
            g2.setColor(Color.YELLOW);
            
        } else {
            g2.setColor(Color.BLACK);
        }
        g2.setStroke(new BasicStroke(4));
            g2.drawRect(
                (int)((block.getTopX()) * this.getWidth() - (scroll * this.getWidth())) / levelwidth, 
                block.getTopY() * this.getHeight() / levelheight, 
                width * this.getWidth() / levelwidth, //Only showing 20 blocks at max
                this.getHeight()* height / levelheight);
        
       // System.out.println(scroll);
       // System.out.println(block.getTopY());
    }
    public void update(Object observable){
        
    }

 
    
  
    
    
}