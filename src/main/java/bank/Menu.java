package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scanner;
 
  
  public static void main (String[] args){
    System.out.println("Welcome to Global Bank");


    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }




    menu.scanner.close();
  }

  private Customer authenticateUser(){
    System.out.println("Please enter username");
    String username = scanner.next();

    System.out.println("Please enter passowrd");
    String password = scanner.next();

    Customer customer =null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }

    return customer;
  }

  private void showMenu(Customer customer, Account account){

    int selection =0

    while(selection != 4 && customer.isAuthenitcated()){
      System.out.println("===============");
      System.out.println("please select option");
      System.out.println("1: deposit");
      System.out.println("2: withdraw");
      System.out.println("3: check balance");
      System.out.println("4: exit");
      System.out.println("===============");

      selection = scanner.nextInt();
      double amount = 0;

      switch(selection){
        case 1:
        System.out.println("how much do you want to deposit");
        amount = scanner.nextDouble();
        try{account.deposit(amount);
        }catch(AmountException e){
          System.out.println(e.getMessage());
          System.out.println("please try again");
        }
        break;

        case 2:
        System.out.println("how much do you want to withdraw");
        amount = scanner.nextDouble();
        try{
        account.withdraw(amount);
        }catch(AmountException e){
          System.out.println(e.getMessage());
          System.out.println("please try again");
        }
        break;

        case 3:
        System.out.println("current balance: " + account.getBalance());
        break;

        case 4:
        Authenticator.logout(customer);
        System.out.println("you have logged out");
        break;

        default:
        System.out.println("Invlaid option. Try again");
      }
    }
  }

}
