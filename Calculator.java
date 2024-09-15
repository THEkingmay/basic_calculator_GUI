import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;
public class Calculator extends JFrame implements ActionListener{
    private JPanel buttonField,displayPanel;
    private JButton[] buttons = new JButton[20];
    private JTextField displayField,smalldisplayField;
    private String[] text = {"CE","C","<","÷","7","8","9","*","4","5","6","-","1","2","3","+","+-","0",".","="};
    private boolean operationClicked = false;
    private Queue<String> word = new LinkedList<>();
    private String display="";
    public Calculator(String title){
        super(title);
        setComponent();
        setSize(300,400);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void setComponent(){
        Container con = this.getContentPane();
        displayField = new JTextField();
        smalldisplayField= new JTextField();

        smalldisplayField.setFont(new Font("Serif", Font.PLAIN, 20));
        smalldisplayField.setPreferredSize(new Dimension(100,30));
        smalldisplayField.setBackground(new Color(230, 230, 230));
        smalldisplayField.setDisabledTextColor(Color.BLUE);
        smalldisplayField.setEnabled(false);

        displayField.setPreferredSize(new Dimension(100,60));
        displayField.setFont(new Font("Serif", Font.BOLD, 30));
        displayField.setEnabled(false);
        displayField.setDisabledTextColor(Color.BLACK);
        displayField.setBackground(new Color(230, 230, 230));
        
        displayField.setBorder(null);
        smalldisplayField.setBorder(null);

        displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(displayField,BorderLayout.SOUTH);
        displayPanel.add(smalldisplayField,BorderLayout.NORTH);

        con.add(displayPanel, BorderLayout.NORTH);
        setButton(con);
    }
    public void setButton(Container con){
        buttonField = new JPanel();
        buttonField.setLayout(new GridLayout(5,4,5,5));
        for(int i=0 ; i<text.length ; i++){
            buttons[i]= new JButton(text[i]);
            buttons[i].addActionListener(this);
            buttons[i].setFont(new Font("Serif", Font.BOLD, 20));
            buttons[i].setBackground(Color.WHITE);
            buttonField.add(buttons[i]);
        }
        con.add(buttonField);
    }
    public void calSum(){
        double num1 = Double.parseDouble(word.remove());
        double result=0;
        switch (word.remove()) {
            case "+" :
            result=num1+Double.parseDouble(word.remove());
            break;
            case "-" :
            result=num1-Double.parseDouble(word.remove());
            break;
            case "*" :
            result=num1*Double.parseDouble(word.remove());
            break;
            case "÷" :
            double num2 = Double.parseDouble(word.remove());
            if(num2==0){
                displayField.setText("Cannot divide bt zero");
                display="0";
            }else{
                result=num1/num2;
            }
            break;
        }
        display=result+"";
        displayField.setText(result+"");
        operationClicked=false;
    }
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton)e.getSource();
        switch ( clickedButton.getText() ){
            case "CE":
                if(!word.isEmpty()){
                    display="";
                    displayField.setText(display);
                }
                break;
            case "C":
                display="";
                displayField.setText(display);
                smalldisplayField.setText(display);
                operationClicked=false;
                while(!word.isEmpty()){
                    word.remove();
                }
                break;
            case "<":
                try{
                    display=display.substring(0,display.length()-1);
                    displayField.setText(display);
                }catch(Exception err){
                    System.out.println(err);
                }
                break;
            case "+":case "-":case "*": case "÷":
                if(!(displayField.getText().equals(""))){
                    if(!operationClicked){
                        word.add(display);
                        word.add(clickedButton.getText());
                        smalldisplayField.setText(display+=clickedButton.getText());
                        display="";
                        displayField.setText(display);
                        operationClicked=!operationClicked;
                    }
                }else{ // change the operator
                    display+=word.remove();
                    word.remove();
                    word.add(display);
                    word.add(clickedButton.getText()); // change operator
                    smalldisplayField.setText(display+=clickedButton.getText());
                    display="";
                }
                break;
            case "0": case "1": case "2": 
            case "3": case "4": case "5":
            case "6": case "7": case "8": 
            case "9": case ".": 
                if(clickedButton.getText().equals(".")){
                    if(display.equals(""))
                    return;
                    for(char c : display.toCharArray()){
                        if(c=='.')return;
                    }
                }
                displayField.setText(display+=clickedButton.getText());
                break;
            case "=":
                if((!word.isEmpty()) && !(displayField.getText().equals(""))){
                    word.add(display);
                    smalldisplayField.setText(smalldisplayField.getText()+display);
                    calSum();
                }
                break;
            case "+-":
                if(display!=""){
                    double tmp=Double.parseDouble(display);
                    tmp*=-1;
                    display=tmp+"";
                    displayField.setText(display);
                }
                break;
        }
    }
    public static void main(String[] args) {
        new Calculator("my cal");
    }
}