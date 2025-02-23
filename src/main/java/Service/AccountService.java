package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    AccountDAO accService; 

    public AccountService() {
        accService = new AccountDAO();
    }

    public AccountService(AccountDAO ad) {
        this.accService = ad;
    }

    //get account, return null if no user and returns a user if it they exist
    public Account getAccount(Account a) {
        return null;
    }
    
    public Account createAccount(Account a) {
        return null;
    }
}
