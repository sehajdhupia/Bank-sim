import javax.swing.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;  
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

public class WindowTransactions extends JFrame {

  private JPanel contentPane;
  private JLabel endT;
  private JTextField textFieldOne;
  private JTextField textFieldTwo;
  private BankAccount chequing;
  private BankAccount savings;
  private String accountName;
  private JTextArea transactions;
  private JScrollPane scrollPane;


  // Creating frame
  public WindowTransactions(String accountName,BankAccount chequing,BankAccount savings) {
    // account information
    this.chequing = chequing; 
    this.savings = savings;
    this.accountName = accountName;
    setTitle("Transactions"); 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(0, 0, 330, 330); 
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null); 
    setContentPane(contentPane);

    JLabel lblNumberOne = new JLabel("Start Time");  
    lblNumberOne.setBounds(5, 30, 90, 21);
    contentPane.add(lblNumberOne);
    endT = new JLabel("End Time");
    endT.setBounds(5, 55, 90, 21);
    contentPane.add(endT);
    textFieldOne = new JTextField();
    textFieldOne.setBounds(90, 30, 125, 20); 
    contentPane.add(textFieldOne);
    textFieldTwo = new JTextField();
    textFieldTwo.setBounds(90, 55, 125, 20); 
    contentPane.add(textFieldTwo);
    //formatting input
    JLabel form = new JLabel(" YYYY-MM-DD "); 
    form.setBounds(100,10,125,20);
    contentPane.add(form);
    JButton btnCalculate = new JButton("Find");
    btnCalculate.addMouseListener(new MouseAdapter() { //running method when find is pressed
      public void mouseClicked(MouseEvent e) {
        find();
      }
    });
    btnCalculate.setBounds(220, 28, 100, 46);
    contentPane.add(btnCalculate);
    JButton cancel = new JButton("Cancel");
    cancel.setBounds(115, 270, 100, 25);
    contentPane.add(cancel);
    cancel.addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent e) {//running method when canceled is pressed
        returnBack();
      }
    });
    transactions= new JTextArea(); 
    transactions.setEditable(false); 
    scrollPane = new JScrollPane(transactions);
    scrollPane.setBounds(10,80,310,180);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    contentPane.add(scrollPane);
    }
  public void find() {
    transactions.setText("");
    ArrayList<Transaction> depWith = new ArrayList(); //creating a arraylist to hold deposits and withdrawals
    String endTime = String.valueOf(textFieldTwo.getText()); //end time user input 
    String startTime = String.valueOf(textFieldOne.getText()); //start time user input 
    DateTimeFormatter formatting= DateTimeFormatter.ofPattern("yyyy-MM-dd"); //formatting input
    
    if (startTime.equals("")){ //setting no input as null
      startTime=null;
    } 
    if  (endTime.equals("")){
      endTime=null;
    } 
    if (startTime!=null && endTime!=null){  
      LocalDate startDate = LocalDate.parse(startTime, formatting);
      LocalDate endDate = LocalDate.parse(endTime, formatting);
      LocalDateTime startDateTime=LocalDateTime.of(startDate, LocalDateTime.now().toLocalTime()); 
      LocalDateTime endDateTime=LocalDateTime.of(endDate, LocalDateTime.now().toLocalTime());
      //parsing inputs into a localdatetime variable 
      if (accountName.equals("Chequing")){ 
        ArrayList<Transaction> transactions = chequing.getTransactions(startDateTime, endDateTime); //creating a arraylist of transactions for chequing account 
        depWith=transactions; //storing them in the arraylist
      }else if (accountName.equals("Savings")){
        ArrayList<Transaction> transactions = savings.getTransactions(startDateTime, endDateTime);
        //creating a arraylist of transactions for the savings account 
        depWith=transactions; //storing them in the arraylist
      }
    }else if (startTime==null && endTime!=null){ //if start time is null
      LocalDate endDate = LocalDate.parse(endTime, formatting); 
      LocalDateTime endDateTime=LocalDateTime.of(endDate, LocalDateTime.now().toLocalTime());
      
      if (accountName.equals("Chequing")){
        ArrayList<Transaction> transactions = chequing.getTransactions(null, endDateTime);
        depWith=transactions;
      }else if (accountName.equals("Savings")){
        ArrayList<Transaction> transactions = savings.getTransactions(null, endDateTime);
        depWith=transactions;
      }
    }else if (startTime!=null && endTime==null) { //if endtime is null
      LocalDate startDate = LocalDate.parse(startTime, formatting);
      LocalDateTime startDateTime=LocalDateTime.of(startDate, LocalDateTime.now().toLocalTime());
      //only an startDateTime variable is created because the end time is null
      if (accountName.equals("Chequing")){
        ArrayList<Transaction> transactions = chequing.getTransactions(startDateTime, null);
        depWith=transactions;
      }else if (accountName.equals("Savings")){
        ArrayList<Transaction> transactions = savings.getTransactions(startDateTime, null);
        depWith=transactions;
      }
    }else if (startTime==null && endTime==null){ //if both inputs are null
    
      //neither localdatetime variables are created because the startTime and endTime are null
      if (accountName.equals("Chequing")){
        ArrayList<Transaction> transactions = chequing.getTransactions(null, null);
        depWith=transactions;
      }else if (accountName.equals("Savings")){
          ArrayList<Transaction> transactions = savings.getTransactions(null, null);
          depWith=transactions;
      }
    }
    DateTimeFormatter formatters= DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"); //formatting output
    for(int i=0; i<depWith.size(); i++){ //loop until all the transactions are outputted
      double bal = depWith.get(i).getAmount(); //retrieves the double value amount for each transaction
      String finalBalance = String.format("%.2f",bal); //formatting into a string
      transactions.append(depWith.get(i).getDescription()+": "+depWith.get(i).getTransactionTime().format(formatters)+": $"+finalBalance+"\n"); 
    }
  }
  public void returnBack(){
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}