
package texteditorfinal;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FindAndReplace extends JDialog implements ActionListener{
    boolean foundOne,isReplace;
    JTextField searchText,replaceText;
    JCheckBox cbCase,cbWhole;
    JRadioButton up,down;
    JLabel statusinfo;
    JFrame owner;
    JPanel north,center,south;
    
    public FindAndReplace(JFrame owner,boolean isReplace) {
        super(owner,true);
        this.isReplace=isReplace;
        north=new JPanel();
        center=new JPanel();
        south=new JPanel();
        if (isReplace) {
            setTitle("Find and Replace");
            setReplacePanel(north);
        }
        else{
            setTitle("Find");
            setFindPanel(north);
        }
        addComponent(center);
        statusinfo=new JLabel("Status info");
        south.add(statusinfo);
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
            
        });
        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(south, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void addComponent(JPanel center){
        JPanel east=new JPanel();
        JPanel west=new JPanel();
        center.setLayout(new GridLayout(1,2));
        east.setLayout(new GridLayout(2,1));
        west.setLayout(new GridLayout(2,1));
        cbCase=new JCheckBox("Match Case",true);
        cbWhole=new JCheckBox("Match Word",true);
        ButtonGroup group=new ButtonGroup();
        up=new JRadioButton("Search up",false);
        down=new JRadioButton("Search down",true);
        group.add(up);
        group.add(down);
        east.add(cbCase);
        east.add(cbWhole);
        east.setBorder(BorderFactory.createTitledBorder("Search Options: "));
        west.add(up);
        west.add(down);
        west.setBorder(BorderFactory.createTitledBorder("Search Direction: "));
        center.add(east);
        center.add(west);
    }
    private void setFindPanel(JPanel north){
        final JButton NEXT = new JButton("Find Next");
        NEXT.addActionListener(this);
        NEXT.setEnabled(false);
        searchText=new JTextField(20);
        searchText.addActionListener(this);
        searchText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                boolean state = (searchText.getDocument().getLength()>0);
                NEXT.setEnabled(state);
                foundOne=false;
            }
            
        });
        if (searchText.getText().length()>0) {
            NEXT.setEnabled(true);
            
        }
        north.add(new JLabel("Find word:"));
        north.add(searchText);
        north.add(NEXT);
    }
    private void setReplacePanel(JPanel north){
        GridBagLayout grid=new GridBagLayout();
        north.setLayout(grid);
        GridBagConstraints con= new GridBagConstraints();
        con.fill=GridBagConstraints.HORIZONTAL;
        JLabel lblFword=new JLabel("Find word: ");
        JLabel lblRword=new JLabel("Replace with: ");
        final JButton NEXT =new JButton("Replace Text");
        NEXT.addActionListener(this);
        NEXT.setEnabled(false);
        searchText=new JTextField(20);
        replaceText=new JTextField(20);
        replaceText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                super.keyReleased(ke); 
                boolean state = (replaceText.getDocument().getLength()>0);
                NEXT.setEnabled(state);
                foundOne=false;
            }
            
        });
        con.gridx=0;
        con.gridy=0;
        grid.setConstraints(lblFword, con);
        north.add(lblFword);
        con.gridx=1;
        con.gridy=0;
        grid.setConstraints(searchText, con);
        north.add(searchText);
        
        con.gridx=0;
        con.gridy=1;
        grid.setConstraints(lblRword, con);
        north.add(lblRword);
        con.gridx=1;
        con.gridy=1;
        grid.setConstraints(replaceText, con);
        north.add(replaceText);
        con.gridx=2;
        con.gridy=1;
        grid.setConstraints(NEXT, con);
        north.add(NEXT);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(searchText)||ae.getSource().equals(replaceText)){
            validate();
        }
        process();
    }
    
    public void process(){
        if(isReplace){
            statusinfo.setText("Replacing "+searchText.getText());
        }
        else{
            statusinfo.setText("Searching for "+searchText.getText());
        }
        int caret=NewPane.getArea().getCaretPosition();
        String word=getWord();
        String text=getAllText();
        caret=search(text, word, caret);
        if(caret<0){
            endResult();
        }
    }
    private void endResult(){
        String message="";
        String str = "";
        if (isSearchDown()) {
            str = "Search down";
        } else {
            str = "Search up";
        }
        if (foundOne && !isReplace) {
            message = "End of " + str + " for " + searchText.getText();
        } else if (foundOne && isReplace) {
            message = "End of replace " + searchText.getText() + " with " + replaceText.getText();
        } else {
            message = "No results for " + "'" + searchText.getText() + "'";
        }
        statusinfo.setText(message);
    }
    private int search(String text, String word, int caret){
        boolean found=false;
        int all = text.length();
        int check = word.length();
        if(isSearchDown()){
            System.out.println(caret);
            for (int i = caret; i<=(all-check); i++){
                String temp=text.substring(i, (i+check));
                if(temp.equals(word)){
                    if(wholeWordIsSelected()){
                        if(checkForWholeWord(check, text, i)){
                            caret=i;
                            found=true;
                            break;
                        }
                    }
                    else{//not whole word
                        caret=i;
                        System.out.println(caret);
                        found=true;
                        break;
                    }
                }
            }
        }
        else{
            System.out.println(caret);
            for (int i = caret-1; i >= check; i--) {
                String temp=text.substring((i-check), i);
                if(temp.equals(word)){
                    if(wholeWordIsSelected()){
                        if(checkForWholeWord(check, text, (i-check))){
                            caret=i;
                            found=true;
                            break;
                        }
                    }
                    else{//not whole word
                        caret=i;
                        found=true;
                        break;
                    }
                }
            }
        }
        NewPane.getArea().setCaretPosition(0);
        if(found){
            NewPane.getArea().requestFocus();
            if(isSearchDown()){
                NewPane.getArea().select(caret, (caret+check));
            }
            else{
                NewPane.getArea().select((caret-check),caret);
            }
            if(isReplace){
            String replace=replaceText.getText();
            NewPane.getArea().replaceSelection(replace);
            if(isSearchDown()){
                NewPane.getArea().select(caret, caret+replace.length());
            }
            else{
                NewPane.getArea().select(caret-check, ((caret-check)+replace.length()));
            }
        }
            foundOne=true;
            return caret;
        }
        //for replacing
        
        return -1;
    }
    
    private String getAllText(){
        if(caseNotSelected()){
            return NewPane.getArea().getText().toLowerCase();
        }
        return NewPane.getArea().getText();
    }
    
    private String getWord(){
        if(caseNotSelected()){
            return searchText.getText().toLowerCase();
        }
        return searchText.getText();
    }
    
    private boolean caseNotSelected(){
        return !cbCase.isSelected();
    }
    
    private boolean isSearchDown(){
        return down.isSelected();
    }
    private boolean wholeWordIsSelected(){
        return cbWhole.isSelected();
    }
    private boolean checkForWholeWord(int check,String text,int cursor){
        int offsetLeft=cursor-1;
        int offsetRight=cursor+check;
        if(offsetLeft<0){
            if(offsetRight>=text.length()){
                return true;
            }
            else if(!Character.isLetterOrDigit(text.charAt(offsetRight))){
            return true;
            }
            else{
                return false;
            }
        }
        else if(((offsetRight)>=text.length())){
            if(!Character.isLetterOrDigit(text.charAt(offsetLeft))){
                return true;
            }
            else{
                return false;
            }
        }
        else if(!Character.isLetterOrDigit(text.charAt(offsetLeft))&&
                    !Character.isLetterOrDigit(text.charAt(offsetRight))){
            return true;
        }
        else{
            return false;
        }
    }
}
