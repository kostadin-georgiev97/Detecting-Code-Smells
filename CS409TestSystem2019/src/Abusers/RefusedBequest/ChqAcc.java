package Abusers.RefusedBequest;

public class ChqAcc extends Account {

    protected double minBalance;

    public ChqAcc() {
        super();
        minBalance = 0;
    }

    public ChqAcc(double initBal , double limit) {
        super(initBal);
        minBalance = limit;
    }

    // Refused bequest
    public boolean withdraw(double amount) {
        if ( balance - amount >= minBalance )
        {
            balance = balance - amount;
            return true;
        }
        else
            return false;
    }

    public void setLimit(double limit) {
        minBalance = limit;
    }

    public double getLimit() {
        return minBalance;
    }

    public void serviceCharge() {
        if (balance < 2 * minBalance)
            super.serviceCharge();
    }

    public String toString() {
        return super.toString() + " Limit : " + minBalance;
    }

}
