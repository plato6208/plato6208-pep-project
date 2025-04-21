package DAO;
import Model.Message;
import Util.ConnectionUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    //helper function that searches for a message given an ID and returns message if found null if not
    public Message search(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message ret = new Message();
        try {
            //sql statment 
            String check  = "Select * from Message where message_id = ?";
            PreparedStatement ps1 = connection.prepareStatement(check);

            //setting values 
            ps1.setInt(1,id);

            //executes and returns new message if there is a message
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()) {
                ret = new Message(rs1.getInt("message_id"),
                rs1.getInt("posted_by"),
                rs1.getString("message_text"),
                rs1.getLong("time_posted_epoch"));
                return ret;
            }
        }catch(SQLException e) {
            System.out.println("message not found");
        }
        //returns null if no message
        return null;
    }
    //creates a message given parameter Message
    public Message createMessage(Message m){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //sql statment
            String sql = "insert into Message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            //setting up values to pass into sql statment
            ps.setInt(1, m.getPosted_by());
            ps.setString(2, m.getMessage_text());
            ps.setLong(3, m.getTime_posted_epoch());

            //executes 
            ps.executeUpdate();

            //returns a new message if message was found
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int mID = (int) rs.getLong(1);
                return new Message(mID, m.getPosted_by(),m.getMessage_text(),m.getTime_posted_epoch());
            }
        }catch(SQLException e) {
            System.out.println("Message does not exist");
        }

        //returns null if no message
        return null;
    }
    //retrives all messages via list 
    public List<Message> retriveAllMessage(){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            //sql statment
            String sql = "Select * from Message";
            PreparedStatement ps = connection.prepareStatement(sql);

            //executes and returns a list of all messages
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {//retrieve all messages 
                Message mes = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                messages.add(mes);
            }

        } catch(SQLException e) {
            System.out.println("Message does not exist");
        }

        //return a list of all messages or an empty list
        return messages;
    }
    //retrives a message by its message id and returns said message
    public Message retriveByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message mes = new Message();
        try {
            //sql statment
            String sql = "select * from Message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            //setting values
            ps.setInt(1, id);

            //executes and returns a new message
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mes = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                return mes;
            }
        } catch (SQLException e){
            System.out.println("Message does not exist");
        }

        //returns null if no message
        return null;
    }
    //deletes a message given a message id and returns the deleted message
    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();

        //using helper function to search for message
        Message m = search(id);
        try {
            //if the message is found we delete
            if(m != null) {
                String sql = "delete from Message where message_id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("message not found");
        }

        //returns either the deleted message or null 
        return m;
    }

    //updates a message given its message id and the new message
    public Message updateMessage(Message mes, int id){
        Message mess = null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            //check to see if message exist returns null if not
            if (search(id) == null) {
                return null;
            }

            //sql statment
            String sql = "update Message set message_text = ? where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            //setting values for sql statment 
            ps.setString(1, mes.getMessage_text());
            ps.setInt(2, id);

            //executes and returns the updated message
            int rowUpdated = ps.executeUpdate();
            if(rowUpdated > 0) {
                return search(id);
            }
        } catch(SQLException e) {
            System.out.println("message not found");
        }
        return mess;
    }

    //returns a list of all messages sent by a given user id
    public List<Message> userIDMessage(int posted_by) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            //sql statment 
            String sql = "Select * from Message where posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            //setting values for sql statment
            ps.setInt(1, posted_by);

            //executes and adds all messages int a list 
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Message mes = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
                messages.add(mes);
            }
        } catch(SQLException e) {
            System.out.println("message not found");
        }

        //returns either a list of messages or an empty list 
        return messages;
    }
}
