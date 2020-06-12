package Couplers.MiddleMan;
// from https://lostechies.com/seanchambers/2009/08/28/refactoring-day-29-remove-middle-man/
// This is the middle man which just passes on the request

public class AccountManager {
    public AccountDataProvider DataProvider;

    public AccountManager(AccountDataProvider dataProvider){
        DataProvider = dataProvider;
    }

    public Account GetAccount(int id){
        return DataProvider.GetAccount(id);
    }
}
