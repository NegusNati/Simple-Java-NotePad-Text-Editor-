/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import static java.util.Locale.filter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author wku-cslab1
 */
 public class TextEditor extends JFrame implements ActionListener {

    JTextArea textArea;//declare text area
    JScrollPane scrollPane; // declare a ScrollPane
    JSpinner fontSizeSpinner; // declare JSpinner for size on text area
    JLabel spinnerLabel; // to lable 
    JButton colorChooser; // jButton
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
  
     
     TextEditor(){ 
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to close the app
         this.setTitle("NOTEPAD");//to rename the title
         this.setSize(510,510);//size of the of the app window 
         this.setLayout(new FlowLayout());// to set Flow lay out
         this.setLocationRelativeTo(null);// to not start the app at the top corner of the page
         //this.setResizable(false); //not to allow changing frame size
         
         
         textArea = new JTextArea();//instantiation of the text area
         textArea.setLineWrap(true); // so it won't continue in one line
         //textArea.setWrapStyleWord(true);
         textArea.setFont(new Font("Arial",Font.PLAIN,30));
         
         
         scrollPane =new JScrollPane(textArea);
         scrollPane.setPreferredSize(new Dimension(460,460));// since the textArea is on the ScrollPane it will have the same size
         scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                
         spinnerLabel = new JLabel("Font Size :");
         fontSizeSpinner = new JSpinner();// instansiate JSpinner
         //fontSizeSpinner.setPreferredSize(new Dimension(40,30)); //
         fontSizeSpinner.setValue(30); //default size of the text
         fontSizeSpinner.addChangeListener(new ChangeListener() {
             @Override
             public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN, (int) fontSizeSpinner.getValue()));
             }
         });
         
         colorChooser = new JButton("Font Colors");
         colorChooser.addActionListener(this);
         colorChooser.setFocusable(false);
         
         
         String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
         fontBox = new JComboBox(fonts);
         fontBox.setSelectedItem("Arial");
         fontBox.addActionListener(this);
         
         //  our MENU
         menuBar = new JMenuBar();
         fileMenu = new JMenu("File");
         openItem = new JMenuItem("Open");
         saveItem = new JMenuItem("Save");
         
         
         // add to the Menu, the items
         fileMenu.add(openItem);
         fileMenu.add(saveItem);
         // now add to the Menu Bar
         menuBar.add(fileMenu);
         
         openItem.addActionListener(this);
         saveItem.addActionListener(this);
         
         
         this.setJMenuBar(menuBar);
         this.add(fontBox);
         this.add(colorChooser);
         this.add(spinnerLabel);
         this.add(fontSizeSpinner);
         this.add(scrollPane);
         this.setVisible(true);
         
         
     }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==colorChooser){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null,"Choose a color",Color.BLACK);
            
            textArea.setForeground(color); }
        if(e.getSource()==fontBox){
        
        textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));}
        
        
        if(e.getSource()==openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users\\wku-cslab1\\Documents\\NotePad file")); //Default file path is 
            FileNameExtensionFilter filter = new FileNameExtensionFilter("text file","txt");
            fileChooser.setFileFilter(filter);
            int response =fileChooser.showSaveDialog(null); // it is either 0 or 1
            
             if(response == JFileChooser.APPROVE_OPTION){
                 File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                 Scanner fileIn = null;
                 try{
                 fileIn = new Scanner(file);
                 if(file.isFile()){
                     while(fileIn.hasNextLine()){
                     String line = fileIn.nextLine()+"\n";
                     textArea.append(line);}}
                   }
                 catch(Exception e2){
                     e2.printStackTrace();
                 }
                 finally{
                     fileIn.close();
                 }
             
             }
        
        }
        if(e.getSource()==saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users\\wku-cslab1\\Documents\\NotePad file")); //Default file path is 
            
            int response =fileChooser.showSaveDialog(null); // it is either 0 or 1
            
            if(response == JFileChooser.APPROVE_OPTION){
                 File file;
                  PrintWriter fileOutPut = null;
                
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try{
                    fileOutPut =new PrintWriter(file);
                    fileOutPut.println(textArea.getText());
                }
                catch(Exception e1){
                    e1.printStackTrace();
                }finally{
                fileOutPut.close();
                }
                
                
                
            
            }
            
        
        }
     
        
        
    }
    
}

