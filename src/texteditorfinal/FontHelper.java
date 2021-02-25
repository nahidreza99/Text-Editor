/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditorfinal;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Nahid Reza
 */
public class FontHelper extends JDialog implements ListSelectionListener {

    JPanel pan1,pan2,pan3,panFont,panSize,panType;
    JLabel fontLabel,sizeLabel,typeLabel,previewLable,previewLable2,previewLable3;
    JTextField label, fontText,sizeText,typeText;
    JScrollPane fontScroll,typeScroll,sizeScroll;
    JList fontList,sizeList,typeList;
    JButton ok,cancel;
    GridBagLayout gbl;
    GridBagConstraints gbc;
    JFrame owner;
    
    public FontHelper(JFrame owner) {
        super(owner,true);
        setTitle("Choose Font");
        setSize(400, 400);
        setResizable(false);
        gbl=new GridBagLayout();
        
        setLayout(gbl);
        gbc= new GridBagConstraints();
        
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        fontLabel=new JLabel("Fonts:");
        getContentPane().add(fontLabel,gbc);
        
        gbc.gridx=2;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        sizeLabel=new JLabel("Sizes:");
        getContentPane().add(sizeLabel,gbc);
        
        gbc.gridx=3;
        gbc.gridy=1;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        typeLabel=new JLabel("Type:");
        getContentPane().add(typeLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.weightx=2.0;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        fontText=new JTextField("Arial",12);
        getContentPane().add(fontText,gbc);
        
        gbc.gridx=2;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        sizeText=new JTextField("12",4);
        getContentPane().add(sizeText,gbc);
        
        gbc.gridx=3;
        gbc.gridy=2;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        typeText=new JTextField("Regular",6);
        getContentPane().add(typeText,gbc);
        
        gbc.gridx=1;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.weightx=2.0;
        gbc.weighty=2.0;
        gbc.fill=GridBagConstraints.BOTH;
        String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();//Getting system fonts
        fontList=new JList(fonts);
        fontList.setFixedCellWidth(110);
        fontList.addListSelectionListener(this);
        fontList.setSelectedIndex(0);
        fontScroll=new JScrollPane(fontList);
        getContentPane().add(fontScroll,gbc);
        
        gbc.gridx=2;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.weightx=1.0;
        gbc.weighty=2.0;
        
        String[] sizes={"8","10","11","12","14","16","18","20","22","24","28",
            "32","36","38","40","44","48","52","56","60","66","72"};
        sizeList=new JList(sizes);
        sizeList.setFixedCellWidth(20);
        sizeList.addListSelectionListener(this);
        sizeList.setSelectedIndex(3);
        sizeScroll=new JScrollPane(sizeList);
        getContentPane().add(sizeScroll,gbc);
        
        gbc.gridx=3;
        gbc.gridy=3;
        gbc.gridwidth=1;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.weightx=1.5;
        String[] types={"Regular","Bold","Italic","Bold Italic"};
        typeList=new JList(types);
        typeList.setFixedCellWidth(60);
        typeList.addListSelectionListener(this);
        typeList.setSelectedIndex(0);
        typeScroll=new JScrollPane(typeList);
        getContentPane().add(typeScroll,gbc);
        
        gbc.gridx=1;
        gbc.gridy=4;
        gbc.gridwidth=3;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.weightx=1.0;
        gbc.weighty=0.0;
        pan1=new JPanel();
        pan1.setLayout(new FlowLayout());
        previewLable=new JLabel("Preview: ");
        pan1.add(previewLable);
        getContentPane().add(pan1,gbc);
        
        gbc.gridx=1;
        gbc.gridy=5;
        gbc.gridwidth=3;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.weightx=1.0;
        gbc.weighty=0.0;
       gbc.fill=GridBagConstraints.BOTH;
        pan2=new JPanel();
        pan2.setLayout(new FlowLayout());
        label=new JTextField("The quick brown fox jumps over the lazy dog");
        label.setEditable(true);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setFont(new Font("Arial",Font.PLAIN,20));
        pan2.add(label);
        getContentPane().add(pan2,gbc);
        
        gbc.gridx=1;
        gbc.gridy=6;
        gbc.gridwidth=3;
        gbc.gridheight=1;
        gbc.anchor=GridBagConstraints.CENTER;
        pan3=new JPanel();
        pan3.setLayout(new FlowLayout());
        ok=new JButton("OK");
        cancel=new JButton("Cancel");
        pan3.add(ok);
        pan3.add(cancel);
        getContentPane().add(pan3,gbc);
    }

    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
           if(e.getSource()==fontList) {
               Font f1=new Font(String.valueOf(fontList.getSelectedValue()),
                typeList.getSelectedIndex(),
                Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
               fontText.setText(String.valueOf(fontList.getSelectedValue()));
               label.setFont(f1);
           }
           else if(e.getSource()==sizeList){
               Font f2=new Font(String.valueOf(fontList.getSelectedValue()),
                typeList.getSelectedIndex(),
                Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
               sizeText.setText(String.valueOf(sizeList.getSelectedValue()));
               label.setFont(f2);
           }
           else{
               Font f3=new Font(String.valueOf(fontList.getSelectedValue()),
                typeList.getSelectedIndex(),
                Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
               typeText.setText(String.valueOf(typeList.getSelectedValue()));
               label.setFont(f3);
           }
        } catch (Exception ee) {
            
        }
                
    }
    public Font font(){
        Font font = new Font(String.valueOf(fontList.getSelectedValue()),
                typeList.getSelectedIndex(),
                Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
        return font;
    }
    
    public JButton getOk(){
        return ok;
    }
    
    public JButton getCancel(){
        return cancel;
    }
}
