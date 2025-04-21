package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    AccountDAO accDao; 

    //default constructor 
    public AccountService() {
        accDao = new AccountDAO();
    }

    //parameterized constructor 
    public AccountService(AccountDAO ad) {
        accDao = ad;
    }

    //get account, return null if no user and returns a user if it they exist
    public Account getAccount(Account account) {
        Account ret = accDao.getAccount(account);
        return ret;
    }
    //create a user and returns new user info
    public Account createAccount(Account a) {
        //check to see if username is blank or username already exist
        Account check = accDao.search(a.getUsername());
        if (check != null || a.getPassword().length() < 4 || a.getUsername().isBlank()) {
            return null;
        } else 
            return accDao.createAccount(a.getUsername(), a.getPassword());
    }
}
