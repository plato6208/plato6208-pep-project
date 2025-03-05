package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    //default constructor
    public MessageService() {
        messageDAO = new MessageDAO();
    }
    
    //parameterized constructor 
    public MessageService(MessageDAO md) {
        messageDAO = md;
    }


    //creates a message
    public Message createMessage(Message m) {
        Message md;
        String mess = m.getMessage_text();
        //checks to see if message length is blank or the length of the message is greater than 255
        if (mess.length() > 255 || mess.isBlank()) {
            return null;
        } else {
            md = messageDAO.createMessage(m);
            return md;
        }
    }

    //returns a list of all messages 
    public List<Message> retriveAllMessages() {
        List<Message> mess = new ArrayList<>();
        mess.addAll(messageDAO.retriveAllMessage());
        return mess;
    }

    //retrieves a message by its message id
    public Message retriveByID(int id) {
        Message m = messageDAO.retriveByID(id);
        return m;
    }

    //deletes a message given an id 
    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }

    //update a message given a message and a message id 
    public Message updateMessage(Message mes, int id) {
        //checks to see if new message is empty or its length is greater than 255
        if(mes.getMessage_text().length() > 255 || mes.getMessage_text().isEmpty()) {
            return null;
        } else 
            return messageDAO.updateMessage(mes, id);
    }

    //returns a list of all messages sent by a given user id
    public List<Message> userIDMessage(int posted_by) {
        List<Message> mess = new ArrayList<>();
        mess.addAll(messageDAO.userIDMessage(posted_by));   
        return mess;
    }
}
