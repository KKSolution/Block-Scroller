
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelpView extends JFrame implements Observer {

    private Model model;

    /**
     * Create a new View.
     */
    public HelpView(Model model, MainView mainView) {
        this.setTitle("Side Scroller Game - Help");
        this.setMinimumSize(new Dimension(330, 560));
        this.setSize(400, 650);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener (new WindowAdapter(){
           @Override
           public void windowClosing(WindowEvent e) {
                       
         
                
                HelpView.this.setVisible(false);
                mainView.setVisible(true);
            }
                    
        });
        
        //Create Panel
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new BoxLayout(helpPanel, BoxLayout.Y_AXIS));
       
        //Back to Main button
        JButton backtoMain = new JButton("Back to Main Menu");        
        backtoMain.addActionListener(new ActionListener(){
            
          public void actionPerformed(ActionEvent e)
            {
                HelpView.this.setVisible(false);
                mainView.setVisible(true);
                
            }  
            
        });
        backtoMain.setAlignmentX(Component.LEFT_ALIGNMENT);
        helpPanel.add(backtoMain);
        helpPanel.add(Box.createVerticalStrut(40));
        
        //Information for profs
        JTextArea actualInfo = new JTextArea();
        actualInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        actualInfo.setLineWrap(true);
        actualInfo.setWrapStyleWord(true);
        actualInfo.setOpaque(false);
        actualInfo.setEditable(false);
        String info = new String("");
 
        info += "Controls can be found in the control view, by clicking on the control button in the main menu.";
        info += "The Level Editor is not ccreated as of yet.";
        info += "Resize works on the main menu.";
        actualInfo.setText(info);
        
        
        
        JPanel information = new JPanel();
        information.setLayout(new BoxLayout(information, BoxLayout.LINE_AXIS));
        information.setAlignmentX(Component.LEFT_ALIGNMENT);
        information.setBorder(BorderFactory.createTitledBorder("Information"));
        information.add(actualInfo);
        //this.getContentPane().add(new JScrollPane(information));
        helpPanel.add(information);
        
        
        
        //Finish
        this.add(helpPanel);
        this.model = model;
        model.addObserver(this);
  
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        //System.out.println("Model changed!");
    }
}
