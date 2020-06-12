package Abusers.RefusedBequest;

public class SavingsAcc extends ChqAcc {
    protected double interestRate;

    public SavingsAcc() {
        super();
        minBalance = 500;
        interestRate = 0.03;
    }

    public SavingsAcc(double initBal , double limit, double rate) {
        super(initBal, limit);
        interestRate = rate;
    }

    public void computeInterest(){
        balance = balance + balance * interestRate;
    }

    // Refused bequest - no-op
    public void serviceCharge() {
    }

    public String toString() {
        return super.toString() + " Interest Rate : " + interestRate;
    }
}
