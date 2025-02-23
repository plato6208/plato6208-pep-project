package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO md) {
        messageDAO = md;
    }

    public Message createMessage(Message m) {
        Message md;
        String mess = m.getMessage_text();
        if (mess.length() > 255 || mess.isBlank()) {
            return null;
        } else {
            md = messageDAO.createMessage(m);
            return md;
        }
    }

    public List<Message> retriveAllMessages(Message m) {
        List<Message> mess = new ArrayList<>();
        mess.addAll(messageDAO.retriveAllMessage(m));
        return mess;
    }

    public Message retriveByID(int id) {
        Message m = messageDAO.retriveByID(id);
        return m;
    }

    public Message deleteMessage(int id) {
        return messageDAO.deleteMessage(id);
    }
    public Message updateMessage(String mes, int id) {
        if(mes.length() > 255 || mes.isBlank()) {
            return null;
        } else 
            return messageDAO.updateMessage(mes, id);
    }

    public List<Message> userIDMessage(int posted_by) {
        List<Message> mess = new ArrayList<>();
        mess.addAll(messageDAO.userIDMessage(posted_by));
        return mess;
    }
}
