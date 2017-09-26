
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainView extends JFrame implements Observer {

    private GameState model;
    private HelpView helpview;
    /**
     * Create a new View.
     */
    public MainView(GameState model) {
        // Set up the window.
        this.setTitle("Side Scroller Game - Help");
        this.setMinimumSize(new Dimension(330, 560));
        this.setSize(400, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helpview = new HelpView(model, MainView.this);
        
        //Create Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        
        //Add Label for Main Menu
        JLabel mainMenuLabel = new JLabel("Main Menu");
        mainMenuLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        mainMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(mainMenuLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        //Create Buttons
        
        //Game Button
        JButton startGame = new JButton("Start Game"){
            {
                setSize(200,70);
                setMaximumSize(getSize());
                setPreferredSize(getSize());
                setMinimumSize(getSize());
            }
        };
        
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGame.addActionListener(new ActionListener()
        {
           
            public void actionPerformed(ActionEvent e)
            {
                GameView game = new GameView(model, MainView.this);
                MainView.this.setVisible(false);
             
            }

        });
        mainPanel.add(startGame);
        mainPanel.add(Box.createVerticalStrut(20));
        
        //Level Editor
        JButton levelEditor = new JButton("Level Editor"){
            {
                setSize(200,70);
                setMaximumSize(getSize());
                setPreferredSize(getSize());
                setMinimumSize(getSize());
            }
        };
        levelEditor.addActionListener(new ActionListener()
        {
           
            public void actionPerformed(ActionEvent e)
            {
                EditorView edit = new EditorView(model, MainView.this);
                MainView.this.setVisible(false);
             
            }

        });
        levelEditor.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(levelEditor);
        mainPanel.add(Box.createVerticalStrut(20));
        
        //options Button
        JButton options = new JButton("Options"){
            {
                setSize(200,70);
                setMaximumSize(getSize());
                setPreferredSize(getSize());
                setMinimumSize(getSize());
            }
        };
        options.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(options);
        mainPanel.add(Box.createVerticalStrut(20));
        
        //help button
        JButton helpButton = new JButton("Help"){
            {
                setSize(200,70);
                setMaximumSize(getSize());
                setPreferredSize(getSize());
                setMinimumSize(getSize());
            }
        };
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpButton.addActionListener(new ActionListener()
        {
           
            public void actionPerformed(ActionEvent e)
            {
                MainView.this.setVisible(false);
                
                MainView.this.helpview.setVisible(true);
                
             
            }

        });
        mainPanel.add(helpButton);
        mainPanel.add(Box.createVerticalStrut(20));
        
        //help Exit
        JButton exitButton = new JButton("Exit"){
            {
                setSize(200,70);
                setMaximumSize(getSize());
                setPreferredSize(getSize());
                setMinimumSize(getSize());
            }
        };
        exitButton.addActionListener(new ActionListener()
        {
           
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }

        });
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(exitButton);      
        
       
       
        this.add(mainPanel);
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
               startGame.setMaximumSize(new Dimension(MainView.this.getSize().width / 2, MainView.this.getSize().height * 70 / 600));
               helpButton.setMaximumSize(new Dimension(MainView.this.getSize().width / 2, MainView.this.getSize().height * 70 / 600));
               exitButton.setMaximumSize(new Dimension(MainView.this.getSize().width / 2, MainView.this.getSize().height * 70 / 600));
               options.setMaximumSize(new Dimension(MainView.this.getSize().width / 2, MainView.this.getSize().height * 70 / 600));
               levelEditor.setMaximumSize(new Dimension(MainView.this.getSize().width / 2, MainView.this.getSize().height * 70 / 600));
            
            
            
               startGame.setFont(new Font("Comic Sans MS", Font.BOLD, startGame.getSize().width /100 + 10));
               helpButton.setFont(new Font("Comic Sans MS", Font.BOLD, helpButton.getSize().width /100 + 10));
               exitButton.setFont(new Font("Comic Sans MS", Font.BOLD, exitButton.getSize().width /100 + 10));
               options.setFont(new Font("Comic Sans MS", Font.BOLD, options.getSize().width /100 + 10));
               levelEditor.setFont(new Font("Comic Sans MS", Font.BOLD, levelEditor.getSize().width /100 + 10));
               
            }
        });
        this.setVisible(true);
        
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        this.model = (GameState)observable;
    }
}
