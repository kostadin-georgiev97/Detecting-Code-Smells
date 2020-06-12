package Couplers.MiddleMan;
// from https://lostechies.com/seanchambers/2009/08/28/refactoring-day-29-remove-middle-man/

public class AccountDataProvider {
    private Account accountId;

    public AccountDataProvider(Account acc){
        accountId = acc;
    }

    public Account GetAccount(int id){
        return accountId;
    }
}
