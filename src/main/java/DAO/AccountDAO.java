package DAO;

import Model.Account;
import Util.ConnectionUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.javalin.Javalin;
//create an account
//search for an account
//delete account
public class AccountDAO {

    public Account getAccount(String username, String password) {
        Account a = new Account();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from Account where username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                return a;
            }
        } catch(SQLException e) {
            System.out.println("Account does not exist");
        }
        return null;
    }
    public Account createAccount(Account a) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into Account (username, password) values (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int aID = (int) rs.getLong(1);
                return new Account(aID, a.getUsername(),a.getPassword());
            }
        } catch(SQLException e) {
            System.out.println("could not create account");
        }

        return null;
    }

}
