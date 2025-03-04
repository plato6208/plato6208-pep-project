package DAO;
import Model.Message;
import Util.ConnectionUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import io.javalin.Javalin;
public class MessageDAO {
    //create message
    //retreive all messages
    //retrive message by its id
    //delete message identified by message id
    //update message text identified by its message id
    //retrieve all messages by particular user
    public Message search(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message ret = new Message();
        try {
            String check  = "Select * from Message where message_id = ?";
            PreparedStatement ps1 = connection.prepareStatement(check);
            ps1.setInt(1,id);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()) {
                ret = new Message(rs1.getInt("message_id"),
                rs1.getInt("posted_by"),
                rs1.getString("message_text"),
                rs1.getLong("time_posted_epoch"));
            }
        }catch(SQLException e) {
            System.out.println("message not found");
        }
            return ret;
    }
    public Message createMessage(Message m){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into Message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, m.getPosted_by());
            ps.setString(2, m.getMessage_text());
            ps.setLong(3, m.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int mID = (int) rs.getLong(1);
                return new Message(mID, m.getPosted_by(),m.getMessage_text(),m.getTime_posted_epoch());
            }
        }catch(SQLException e) {
            System.out.println("Message does not exist");
        }
        return null;
    }
    public List<Message> retriveAllMessage(){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Select * from Message";
            PreparedStatement ps = connection.prepareStatement(sql);
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
        return messages;
    }
    public Message retriveByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        Message mes = new Message();
        try {
            String sql = "select * from Message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mes = new Message(rs.getInt("message_id"),
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"),
                                            rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e){
            System.out.println("Message does not exist");
        }
        return mes;
    }
    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message m = search(id);
        try {
            if(m != null) {
                String sql = "delete from Message where message_id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch(SQLException e) {
            System.out.println("message not found");
        }
        return m;
    }
    public Message updateMessage(Message mes, int id){
        Message mess = null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            //check to see if message exist
            String check  = "Select * from Message where message_id = ?";
            PreparedStatement ps1 = connection.prepareStatement(check);
            ps1.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();
            if(!rs1.next()) {
                return null;
            }

            String sql = "update from Message set message_text = ? where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mes.getMessage_text());
            ps.setInt(2, id);
            int rowUpdated = ps.executeUpdate();
            if(rowUpdated > 0) {
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    mess = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                }
            }
        } catch(SQLException e) {
            System.out.println("message not found");
        }
        return mess;
    }

    public List<Message> userIDMessage(int posted_by) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Select * from Message where posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, posted_by);
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
        return messages;
    }
}
