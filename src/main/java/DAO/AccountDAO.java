package DAO;

import Model.Account;
import Util.ConnectionUtil;


import java.sql.*;


public class AccountDAO {

    public Account getAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //sql statment 
            String sql = "select * from Account where username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            //setting values for sql statment 
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            //executes and returns the found user
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"),
                rs.getString("username"), 
                rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println("Account does not exist");
        }

        //if no user was found then return null
        return null;
    }
    public Account createAccount(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            //sql statment 
            String sql = "insert into Account (username, password) values (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //setting values for sql statment 
            ps.setString(1, username);
            ps.setString(2, password);

            //executes and returns the newly genereated user
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int aID = (int) rs.getLong(1);
                return new Account(aID, username, password);
            }
        } catch(SQLException e) {
            System.out.println("could not create account");
        }

        //returns null if failed
        return null;
    }

    //helper function for account service file
    public Account search(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from Account where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println("could not create account");
        }
        return null;
    }
}
