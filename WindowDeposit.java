import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class WindowDeposit extends JFrame {

  private JPanel contentPane;
  private JTextField textFieldOne;
  private BankAccount chequing;
  private BankAccount savings;
  private String accountName;

  //launching the frame
  public WindowDeposit(String accountName,BankAccount chequing,BankAccount savings) {

    //account information
    this.chequing = chequing; 
    this.savings = savings;
    this.accountName = accountName;
    //setting up window
    setTitle("Depositing");  
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(0, 0, 330, 330); 
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null); 
    setContentPane(contentPane);

    JLabel lblNumberOne = new JLabel("Amount");  
    lblNumberOne.setBounds(65, 46, 90, 21);
    contentPane.add(lblNumberOne);

    textFieldOne = new JTextField();
    textFieldOne.setBounds(160, 46, 96, 20); 
    textFieldOne.setColumns(10); 
    contentPane.add(textFieldOne);
    JButton btnCalculate = new JButton("Done");
    //when button is pressed to calculate balance 
    btnCalculate.addMouseListener(new MouseAdapter() { 
      public void mouseClicked(MouseEvent e) {
        calculate();
      }
    });
    btnCalculate.setBounds(115, 155, 100, 25);
    contentPane.add(btnCalculate);

    JButton cancel = new JButton("Cancel");
    cancel.setBounds(115, 225, 100, 25);
    contentPane.add(cancel);
    cancel.addMouseListener(new MouseAdapter() { //returning back to windowOne if button is clicked  
      public void mouseClicked(MouseEvent e) {
        returnBack();
      }
    });
  }
  //updating balance 
  public void calculate() { 
    double currentBalance = Double.valueOf(textFieldOne.getText());
    if(accountName.equals("Chequing")){
      chequing.deposit(currentBalance,"Deposit");     
//description for transactions
    } else {
      savings.deposit(currentBalance,"Deposit");
    }
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
  public void returnBack(){
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}