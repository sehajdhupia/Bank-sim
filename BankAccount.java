import java.time.LocalDateTime;
import java.util.ArrayList;
public class BankAccount{
  public String accountNumber; 
  public double balance;
  public double withdrawalFee;
  public double annualInterestRate;
  
  //accessors
  public String getAccountNumber(){ 
    return accountNumber;
  }
  public double getBalance(){ 
    return balance;
  }
  public double getAnnualInterestRate(){ 
    return annualInterestRate;
  }
    public double getWithdrawalFee() {
    return withdrawalFee;
  }
  
  //modifiers
  public void setAnnualInterestRate(double annualInterestRate){ 
    this.annualInterestRate = annualInterestRate;
  }
  public void setWithdrawalFee(double withdrawalFee){ 
    this.withdrawalFee=withdrawalFee;
  }
  
  //constructors
  public BankAccount(String accountNumber){ 
    this.accountNumber=accountNumber;
  }
  public BankAccount(String accountNumber,double initialBalance){ 
    this.accountNumber=accountNumber;
    balance=initialBalance;
  }
  public BankAccount(String accountNumber,double balance,double withdrawalFee,double annualInterestRate){ 
    this.accountNumber=accountNumber;
    this.balance=balance;
    this.withdrawalFee=withdrawalFee;
    this.annualInterestRate=annualInterestRate;
  }
  public ArrayList<Transaction> getTransactions = new ArrayList<Transaction>();
  
  public void deposit(LocalDateTime transactionTime, double amount, String description){
    this.balance += amount;
    getTransactions.add(new Transaction(transactionTime, amount, description));
  }
  public void deposit(double amount, String description){
    this.balance += amount;
    getTransactions.add(new Transaction(amount, description));
  }
  public void withdraw(LocalDateTime transactionTime, double amount, String description) {
    balance -= (amount + withdrawalFee);
    getTransactions.add(new Transaction(transactionTime, amount, description));
  }
  public void withdraw(double amount,String description) {
    balance -= (amount + withdrawalFee);
    getTransactions.add(new Transaction(amount,description));
  }
  public ArrayList<Transaction> getTransactions(LocalDateTime startTime, LocalDateTime endTime){
    for(int i = 0 ; i < (getTransactions.size()-1) ; i++) {
      if ((getTransactions.get(i).getTransactionTime()).compareTo(getTransactions.get(i+1).getTransactionTime()) > 0) { 
        getTransactions.add(getTransactions.get(i));
        getTransactions.remove(i);
      }
    }
    if (startTime == null && endTime == null) {
      return getTransactions;
      
    }else if(startTime == null) {
      ArrayList<Transaction> transactionList = new ArrayList<>();
      for (int i = 0; i < getTransactions.size(); i++) {
        if ((getTransactions.get(i).getTransactionTime()).compareTo(endTime) <= 0) {
          transactionList.add(getTransactions.get(i));
        }
      }  
      return transactionList;
    }else if(endTime == null) {
      ArrayList<Transaction> transactionList = new ArrayList<>();
      for (int i = 0; i < getTransactions.size(); i++) {
        if ((getTransactions.get(i).getTransactionTime()).compareTo(startTime) >= 0) {
          transactionList.add(getTransactions.get(i));
        }	
      }  
      return transactionList;
    }else{
      ArrayList<Transaction> transactionList = new ArrayList<>();
      for (int i = 0; i < getTransactions.size(); i++) {
        if ((getTransactions.get(i).getTransactionTime()).compareTo(startTime) >= 0 && (getTransactions.get(i).getTransactionTime()).compareTo(endTime) <= 0) {
          transactionList.add(getTransactions.get(i));
        }
      }  	
        return transactionList;
      }
  }
  public void deposit(double amount){  
    balance += amount;
    getTransactions.add(new Transaction(amount, "Deposited"));
  }
  public void withdraw(double amount){  
    balance -= (amount+withdrawalFee);
    getTransactions.add(new Transaction(amount, "Withdrawn"));
  }
  public boolean isOverDrawn(){ 
    if (balance<0){
      return true; 
    }else{
      return false;
    }
  }
  public String toString(){ 
    if (isOverDrawn()){ 
      return "BankAccount " + accountNumber + ": " + "($"+ String.format("%.2f",Math.abs(balance))+")";
    }else{
      return "BankAccount "+accountNumber+": "+"$" +String.format("%.2f",balance);
    }
  }
}