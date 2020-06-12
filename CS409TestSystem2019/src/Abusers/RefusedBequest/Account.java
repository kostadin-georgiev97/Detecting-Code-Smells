package Abusers.RefusedBequest;

public class Account {

    protected double balance;

    public Account() {
        balance = 0;
    }

    public Account(double bal) {
        balance = bal;
    }

    public double getBalance( ) {
        return balance;
    }

    public void deposit(double dep) {
        balance = balance + dep;
    }

    public boolean withdraw( double amount ) {
        if ( amount <= balance ) {
            balance = balance - amount;
            return true;
        }
        else
            return false;
    }

    public void serviceCharge() {
        balance = balance - 0.50;
    }

    public String toString() {
        return "Balance: " + balance;
    }
}

