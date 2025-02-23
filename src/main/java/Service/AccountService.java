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
        Account ret = accService.getAccount(a);
        return ret;
    }
    //create a user and returns new user info
    public Account createAccount(Account a, String username, String passoword) {
        //check to see if username is blank or username already exist
        if(username == " " || username == a.getUsername()) {
            return null;
        } else {
            Account acc = accService.createAccount(a);
            return acc;
        }
    }
}
