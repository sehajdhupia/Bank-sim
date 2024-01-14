import javax.swing.*; 
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
public class WindowOne extends JFrame implements WindowListener{
  
  //creating an object for each account 
  BankAccount chequing = new BankAccount("Chequing",0,0,0); 
  BankAccount savings = new BankAccount("Savings",0,0,0);
  
//instance variables 
  private JPanel contentPane;
  private JLabel labelHello;
  private JLabel labelHello1;
  private JTextField textField;
  static JComboBox accounts;
  private WindowDeposit two;
  private WindowWithdraw one;
  private WindowTransactions three;
  private String accountName;
  //method that returns the double value of the current balance in either account 
  public double findBal(String accountName) { 
    double bal=0;
    if (accountName.equals("Chequing")) {
      bal = chequing.balance;
    }else if (accountName.equals("Savings")) {
      bal = savings.balance;
    }
    return bal;
  }

  public static void main(String[] args) { 
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          WindowOne frame = new WindowOne();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  //Method to serialize the object chequing or savings into txt files 
  public void serialize(String accountName,BankAccount Account) {
    try { 
      if (accountName.equals("Chequing")) {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("chequing.txt"));
        outputStream.writeObject(Account);
        outputStream.close();
      }else if (accountName.equals("Savings")) {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("savings.txt"));
        outputStream.writeObject(Account);
        outputStream.close();
      }
    }catch (IOException e){
      System.out.println(e); 
    }
  }
  
  //a method to deserialize into codable objects
  public BankAccount deSerialize(String accountName){
    BankAccount z = null;
    try{
      if (accountName.equals("Chequing")) {
        FileInputStream inputStream = new FileInputStream("chequing.txt");
        ObjectInputStream reader = new ObjectInputStream(inputStream);
        z = (BankAccount)reader.readObject();
      }else if (accountName.equals("Savings")) {
        FileInputStream inputStream = new FileInputStream("savings.txt");
        ObjectInputStream reader = new ObjectInputStream(inputStream);
        z = (BankAccount)reader.readObject();
      }
    }catch (IOException e){
      System.out.println(e);
    }catch (ClassNotFoundException e){
      System.out.println(e);
    }
    return z;
  }
  public WindowOne() {
    if (deSerialize("Chequing")==(null)) { //deserializing chequing account object 
      chequing = new BankAccount("Chequing",0,0,0);
    } else {
      chequing = deSerialize("Chequing");
    }
    if (deSerialize("Savings")==(null)) { //deserializing savings account object 
      savings = new BankAccount("Savings",0,0,0);
    } else {
      savings = deSerialize("Savings");
    }
    accountName = "Chequing"; //setting up chequing as initial account as it will appear as the first option on the dropbox
    setTitle("Sehaj's Banking"); //frame setup 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    setBounds(0, 0, 330, 330); 
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
    contentPane.setLayout(null); 
    setContentPane(contentPane);
    //labels
    labelHello = new JLabel("Account:");  
    labelHello.setHorizontalAlignment(JTextField.CENTER); 
    labelHello.setBounds(80, 50, 70, 50);
    contentPane.add(labelHello);
    labelHello1 = new JLabel("Your Current Balance:");    
    labelHello1.setHorizontalAlignment(JTextField.CENTER); 
    labelHello1.setBounds(30, 200, 170, 20);
    contentPane.add(labelHello1);
    textField = new JTextField(""); 
    textField.setEditable(false); 
    textField.setBounds(200, 200, 100, 20); 
    contentPane.add(textField);
    textField.setText("$0.00"); 
    JButton btnPress = new JButton("Deposit"); //buttons
    JButton btnPress1 = new JButton("Withdraw");
    JButton btnPress2 = new JButton("Transactions");
    btnPress.setBounds(35, 235, 100, 30);
    btnPress1.setBounds(190, 235, 110, 30);
    btnPress2.setBounds(95, 150, 150, 30);
    contentPane.add(btnPress);
    contentPane.add(btnPress1);
    contentPane.add(btnPress2);
    //dropbox
    String [] accountSelect={"Chequing","Savings"}; 
    accounts= new JComboBox(accountSelect);
    accounts.setBounds(175, 60, 100, 30);
    contentPane.add(accounts);

    accounts.addActionListener(new ActionListener() { //finding balance from the dropbox 
      //creating bal
      public void actionPerformed(ActionEvent e) {
        double bal = 0; //creating bal 
        if (accounts.getSelectedItem().equals("Savings")) {
          accountName = "Savings"; //resetting the account name 
          bal = savings.balance; //finding balance of savings 
        }else if (accounts.getSelectedItem().equals("Chequing")) {
          accountName = "Chequing";//resetting the account name 
          bal = chequing.balance; //finding balance of chequing 
        }
        if(bal>=0){ 
          textField.setForeground(Color.BLACK);
          textField.setText("$" + String.format("%.2f",bal));
        }else if (bal<0) { 
          textField.setForeground(Color.RED);
          textField.setText("$" + String.format("%.2f",Math.abs(bal)));
        }
      }
    });
    btnPress.addMouseListener(new MouseAdapter() { //opening deposit window if deposit button is pressed
      public void mouseClicked(MouseEvent e) {
        openWindowDeposit();
      }
      
    });
    btnPress1.addMouseListener(new MouseAdapter() { //opening withdraw window if withdraw is pressed
      public void mouseClicked(MouseEvent e) {
        openWithdraw();
      }
    });
    btnPress2.addMouseListener(new MouseAdapter() { //opening transaction window if transaction button is pressed
      public void mouseClicked(MouseEvent e) {
        openTransaction();
      }
    });
  }
  public void openWindowDeposit(){
    two = new WindowDeposit(accountName, chequing, savings); //transferring the data of both accounts and account chosen 
    two.addWindowListener(this);
    two.setVisible(true);
  }
  public void openWithdraw(){
    one = new WindowWithdraw(accountName, chequing, savings);
    one.addWindowListener(this); 
    one.setVisible(true);
  }
  public void openTransaction(){
    three = new WindowTransactions(accountName, chequing, savings);
    three.addWindowListener(this); 
    three.setVisible(true);
  }
  
  public void windowOpened(WindowEvent e) {
  }
  public void windowClosing(WindowEvent e) {
    serialize("Chequing",chequing); //serializing both objects when main window closes 
    serialize("Savings",savings); 
  }
  public void windowClosed(WindowEvent e) {  
    if(findBal(accountName)>=0){ 
      textField.setForeground(Color.BLACK);
      double bal=findBal(accountName);
      textField.setText("$" + String.format("%.2f",bal));
    }else if (findBal(accountName)<0) {
      textField.setForeground(Color.RED);
      double bal=findBal(accountName);
      textField.setText("$" + String.format("%.2f",Math.abs(bal)));
    }
  }
  public void windowIconified(WindowEvent e) {
  }
  public void windowDeiconified(WindowEvent e) {
  }
  public void windowActivated(WindowEvent e) {
  }
  public void windowDeactivated(WindowEvent e) {
  }
}