package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    AccountDAO accDao; 

    public AccountService() {
        accDao = new AccountDAO();
    }

    public AccountService(AccountDAO ad) {
        accDao = ad;
    }

    //get account, return null if no user and returns a user if it they exist
    public Account getAccount(String username, String password) {
        Account ret = accDao.getAccount(username, password);
        return ret;
    }
    //create a user and returns new user info
    public Account createAccount(Account a, String username, String password) {
        //check to see if username is blank or username already exist
        Account check = accDao.search(username);
        if (check != null || password.length() < 4 || username.isBlank()) {
            return null;
        } else 
            return accDao.createAccount(username, password);
    }
}
