/**
 *
 * @author KK
 */


import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
public class GameView extends JFrame implements Observer {   
    private GameState state;
    private JButton pause;
    private JLabel statusLabel;
   // private Canvas game;
    /**
     * Create a new View
     */
    public GameView(GameState state, MainView mainView) {
        // Set up the window.
        this.state = state;
        this.setTitle("Side Scroller Game - Game!");
        this.setMinimumSize(new Dimension(500, 700));
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener (new WindowAdapter(){
           @Override
           public void windowClosing(WindowEvent e) {
                       
         
                
                if (state.getState() == GameState.State.NOTSTARTED ){
                    GameView.this.setVisible(false);
                    mainView.setVisible(true);
                    
                } else {
                    GameState.State oldState = state.getState();
                    GameView.this.state.setState(GameState.State.PAUSED);
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int option;
                    Object[] options = {"Yes","NO"};
                    option = JOptionPane.showOptionDialog(null, 
                            "Game is in progress, are you sure you want to leave?",
                            "IN PROGRESS",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    if (option == 0) {//leave
                        GameView.this.state.setState(GameState.State.NOTSTARTED);  
                        GameView.this.setVisible(false);
                        mainView.setVisible(true);
                    } else {
                        GameView.this.state.setState(oldState); 
                    }
                    
                }
            }
                    
        });
        
        
        //Create base Panel
        JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
        
        
        // State and options Panel
        JPanel gameOptions = new JPanel();
        gameOptions.setLayout(new FlowLayout());
        //gameOptions.setLayout(new FlowLayout());
            //gameOptions.setBorder(BorderFactory.createTitledBorder("Options"));
        //State buttons
        //JPanel stateButtons = new JPanel();
        //stateButtons.setSize(300,300);
       // stateButtons.setMaximumSize(stateButtons.getSize());
    
        //stateButtons.setLayout(new BoxLayout(stateButtons, BoxLayout.Y_AXIS));
        //Back button
        JButton mainMenu = new JButton("Back to main menu");
        
        mainMenu.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e)
            {
                
                if (state.getState() == GameState.State.NOTSTARTED ){
                    GameView.this.setVisible(false);
                    mainView.setVisible(true);
                    
                } else {
                    GameState.State oldState = state.getState();
                    GameView.this.state.setState(GameState.State.PAUSED);
                  
                    int option;
                    Object[] options = {"Yes","NO"};
                    option = JOptionPane.showOptionDialog(null, 
                            "Game is in progress, are you sure you want to leave?",
                            "IN PROGRESS",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                    if (option == 0) {//leave
                        state.setState(GameState.State.NOTSTARTED);  
                        GameView.this.setVisible(false);
                        mainView.setVisible(true);
                    } else {
                        GameView.this.state.setState(oldState); 
                    }
                    
                }
                    
               
             
            }
        });
      
        //Start/Pause/Resume Button
       pause = new JButton("Start");
        pause.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel = new JLabel("Not Started Yet");
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pause.addActionListener(new ActionListener(){
            @Override
             public void actionPerformed(ActionEvent e)
            {
                if (state.getState() == GameState.State.NOTSTARTED){
                    if (GameView.this.state.getBlocks().size() == 0){
                         JOptionPane.showMessageDialog(null, "Need to Load a lvl first", "Load a level", JOptionPane.ERROR_MESSAGE);
                         return;
                    }
                    
                    pause.setText("PAUSE");
                    statusLabel.setText("Game in Progress!");
                    GameView.this.state.setPlayerX(5);
                    GameView.this.state.setPlayerY(GameView.this.state.getHeight()/2);
                    GameView.this.state.setState(GameState.State.STARTED);
                    
                } else if (state.getState() == GameState.State.PAUSED){
                    GameView.this.state.setState(GameState.State.STARTED);
                    pause.setText("PAUSE");
                    statusLabel.setText("Game in Progress!");
                 
                    
                } else {
                    GameView.this.state.setState(GameState.State.PAUSED);
                    pause.setText("RESUME");
                    statusLabel.setText("Game is Paused!");
                }               
             
            }
        });

        gameOptions.add(mainMenu);
        
        gameOptions.add(pause);
        
        gameOptions.add(statusLabel);
        //stateButtons.setBorder(BorderFactory.createTitledBorder("Options"));
        
        
        //Level and optionsPanel
       JPanel levelRow = new JPanel(new FlowLayout());
        JButton chooseLevel = new JButton("Choose Level");
        levelRow.add(chooseLevel);
        JLabel scrollLabel = new JLabel("Scroll Speed");
        JLabel fpsLabel = new JLabel("FPS");
        //optionsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        SpinnerNumberModel scrollModel = new SpinnerNumberModel(1,1,10,1);
        SpinnerNumberModel fpsModel = new SpinnerNumberModel(60,20,60,1);
        JSpinner scrollVal = new JSpinner(scrollModel);
        JSpinner fpsVal = new JSpinner(fpsModel);
        levelRow.add(scrollLabel);
        levelRow.add(scrollVal);
        levelRow.add(fpsLabel);
        levelRow.add(fpsVal);
        JButton submitDimension = new JButton("Change Speed");
        submitDimension.addActionListener(new ActionListener()
        {
           @Override
            public void actionPerformed(ActionEvent e)
            {
                
              state.setScrollSpeed((Integer) scrollVal.getValue());
              state.setFPS((Integer) fpsVal.getValue());
              //editor.levelwidth = (Integer) widthVal.getValue();
            }

        });
        levelRow.add(submitDimension);
       // chooseLevel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //level.setBorder(BorderFactory.createTitledBorder("Options"));
        
      
       
        
        
        chooseLevel.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e)
            {
                JFileChooser levelSelector = new JFileChooser();
                levelSelector.setAcceptAllFileFilterUsed((false));
                 FileNameExtensionFilter levelFilter = new FileNameExtensionFilter("CS349 A1 Levels", "lvl");
                 levelSelector.setFileFilter((levelFilter));
                int selection = levelSelector.showOpenDialog(GameView.this);
                if (selection == JFileChooser.APPROVE_OPTION){
                   File selectedLevel = levelSelector.getSelectedFile();
                   //System.out.println(selectedLevel.getName());
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(selectedLevel));
                        try {
                            String line;
                            while ((line = br.readLine()) != null){
                                if (line.charAt(0) == '#'){
                                    continue;
                                }
                                String[] di = line.split(",");
                                if (di.length == 2){
                                    //Dimension
                                    GameView.this.state.setWidth(Integer.parseInt(di[0]));
                                    GameView.this.state.setHeight(Integer.parseInt(di[1]));
                                    
                                } else {
                                    Block bl = new Block(Integer.parseInt(di[0]),Integer.parseInt(di[1]),Integer.parseInt(di[2]),Integer.parseInt(di[3]));
                                    GameView.this.state.addBlocks(bl);
                                }
                                 
                            };
                            JOptionPane.showMessageDialog(null, "Level loaded!", "Success!", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) { 
                            
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
             
            }
        });
        gameOptions.add(levelRow);
       
         //Game Canvas
        
          GamePanel canvasPanel = new GamePanel(GameView.this.state);
         //canvasPanel.setLayout(new BoxLayout(canvasPanel, BoxLayout.LINE_AXIS));
         
         canvasPanel.setSize(800,600);
       //  canvasPanel.setMaximumSize(canvasPanel.getSize());
         canvasPanel.setMinimumSize(canvasPanel.getSize());
         //canvasPanel
         
         //canvasPanel.add(game);
         //canvasPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
         //canvasPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
         //canvasPanel.setBorder(BorderFactory.createTitledBorder("Options"));
         //canvasPanel.
         
       // stateButtons.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        //level.setAlignmentX(Component.LEFT_ALIGNMENT);
       // gameOptions.add(stateButtons);
        //gameOptions.add(level);
        //gameOptions.setAlignmentX(Component.RIGHT_ALIGNMENT);
        canvasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        overallPanel.add(canvasPanel);
        overallPanel.add(gameOptions);
       
        this.add(overallPanel);
        
        
       
        // Hook up this observer so that it will be notified when the model
        // changes.
      
        state.addObserver(this);
       
        setFocusTraversalKeysEnabled(false);
         GameView.this.setFocusable(true);
        
        
        setVisible(true);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        //System.out.println("Model changed!");
        GameState state = (GameState)(observable);
        if (state.getState() == GameState.State.NOTSTARTED){
                   
        pause.setText("Start");
        statusLabel.setText("NOT STARTED");


        } else if (state.getState() == GameState.State.PAUSED){

            pause.setText("PAUSE");
            statusLabel.setText("Game in Pause!");


        } else {

            pause.setText("RESUME");
            statusLabel.setText("Game in Progress!");
        }               
             
            
    }
}
