

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

public class EditorView extends JFrame implements Observer {   
    private GameState state;
    private JButton pause;
    private JLabel statusLabel;
  
   // private Canvas game;
    /**
     * Create a new View
     */
    public EditorView(GameState state, MainView mainView) {
        // Set up the window.
        super();
        this.state = state;
        this.setTitle("Side Scroller Game - Editor!");
        this.setSize(800, 400);
        this.setMinimumSize(new Dimension(800, 400));
        this.setMaximumSize(new Dimension(800, 400));
       this.addWindowListener (new WindowAdapter(){
           @Override
           public void windowClosing(WindowEvent e) {
                       
         
                
                
                    EditorView.this.setVisible(false);
                    mainView.setVisible(true);
             
            }
                    
        });
        
  this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Create base Panel
        JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
      

        
        EditorPanel editor = new EditorPanel(this.state);
        editor.setSize(800,400);
        editor.setMinimumSize(new Dimension(800,800));
        editor.setAlignmentX(Component.CENTER_ALIGNMENT);
        overallPanel.add(editor);
        JPanel optionsPanel = new JPanel();
       optionsPanel.setLayout(new FlowLayout());
        JLabel widthLabel = new JLabel("Width");
        JLabel heightLabel = new JLabel("Height");
        //optionsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        SpinnerNumberModel maxHeight = new SpinnerNumberModel(10,0,100,1);
        SpinnerNumberModel maxWidth = new SpinnerNumberModel(20,0,100,1);
        JSpinner heightVal = new JSpinner(maxHeight);
        JSpinner widthVal = new JSpinner(maxWidth);
        
        JButton submitDimension = new JButton("Change Dimensions");
        submitDimension.addActionListener(new ActionListener()
        {
           @Override
            public void actionPerformed(ActionEvent e)
            {
                
              editor.levelheight = (Integer) heightVal.getValue();
              editor.levelwidth = (Integer) widthVal.getValue();
            }

        });
        widthLabel.setLabelFor(widthVal);
       heightLabel.setLabelFor(heightVal);
       JButton undoBlock = new JButton("Undo Block");
          JButton redoBlock = new JButton("Redo Block");
       undoBlock.addActionListener(new ActionListener()
        {
           @Override
            public void actionPerformed(ActionEvent e)
            {
              if (editor.blocks.empty()){
                  return;
              }  
              editor.redo.push(editor.blocks.pop());
              redoBlock.setEnabled(true);
            }

        });
     
          redoBlock.addActionListener(new ActionListener()
        {
           @Override
            public void actionPerformed(ActionEvent e)
            {
              if (editor.redo.empty()){
                  return;
              }  
              editor.blocks.push(editor.redo.pop());
              if (editor.redo.empty()){
                  redoBlock.setEnabled(false);
              }
            }

        });
       JButton mode  = new JButton("Toggle Create/Select");
       JLabel modeObv = new JLabel("Create");
             mode.addActionListener(new ActionListener()
        {
           @Override
            public void actionPerformed(ActionEvent e)
            {
              editor.create = !editor.create;
              if (editor.create){
                  modeObv.setText("In Creation Mode");
              } else {
                  modeObv.setText("In Selection Mode");
              }
            }

        });
             JButton saveLevel = new JButton("Save Level");
              saveLevel.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e)
            {
                JFileChooser c = new JFileChooser();
                 int rVal = c.showSaveDialog(EditorView.this);
               if (rVal == JFileChooser.APPROVE_OPTION) {
                   System.out.println("WUT");
                File level = c.getSelectedFile();
                PrintWriter pw = null;
                    try {
                        pw = new PrintWriter(level);
                        
                        for (Block block : editor.blocks){
                            pw.write(String.valueOf(editor.levelwidth) + "," + String.valueOf(editor.levelheight) +  "\n");
                            pw.write(String.valueOf(block.getTopX()) + "," + 
                                    String.valueOf(block.getTopY()) + "," + 
                                            String.valueOf(block.getBotX()) + "," + 
                                                    String.valueOf(block.getBotY())
                            );
                            pw.write("\n");
                              System.out.println("WUT");
                        }
                          pw.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EditorView.class.getName()).log(Level.SEVERE, null, ex);
                    }
              
  
      }
             
            }
        });
       optionsPanel.add(widthLabel);
       optionsPanel.add(widthVal);
       optionsPanel.add(heightLabel);
       optionsPanel.add(heightVal);
       optionsPanel.add(submitDimension);
       optionsPanel.add(undoBlock);
       optionsPanel.add(redoBlock);
       optionsPanel.add(mode);
       optionsPanel.add(modeObv);
       optionsPanel.add(saveLevel);
       overallPanel.add(optionsPanel);
      
        
        this.add(overallPanel);
        
         state.addObserver(this);
       
        setFocusTraversalKeysEnabled(false);
        this.setFocusable(true);
        
        
        setVisible(true);
    }
    @Override
    public void update(Object observable){
        int i = 2;
        return;
    }
    
  
    
}
        
