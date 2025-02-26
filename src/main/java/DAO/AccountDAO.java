package DAO;

import Model.Account;
import Util.ConnectionUtil;


import java.sql.*;

//create an account
//search for an account
//delete account
public class AccountDAO {

    public Account getAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from Account where username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"),
                rs.getString("username"), 
                rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println("Account does not exist");
        }
        return null;
    }
    public Account createAccount(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into Account (username, password) values (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int aID = (int) rs.getLong(1);
                return new Account(aID, username, password);
            }
        } catch(SQLException e) {
            System.out.println("could not create account");
        }

        return null;
    }
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
