package Couplers.MiddleMan;
// from https://lostechies.com/seanchambers/2009/08/28/refactoring-day-29-remove-middle-man/

public class Consumer {
    public AccountManager AccountManager;

    public Consumer(AccountManager accountManager){
        AccountManager = accountManager;
    }

    public void Get(int id){
        Account account = AccountManager.GetAccount(id);
    }
}


