package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Service.AccountService;
import Model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    public SocialMediaController() {
        this.accountService = new AccountService();  // Initialize the account service
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login", this::login);
        app.post("/register", this::register);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void login(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            Account account = mapper.readValue(context.body(), Account.class);
            AccountService accountService = new AccountService();
            Account loginAccount = accountService.getAccount(account);
            
            if (loginAccount == null) {
                context.status(401);
            } else {
                context.json(mapper.writeValueAsString(loginAccount));
            }
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private void register(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            Account account = mapper.readValue(context.body(), Account.class);
            AccountService accountService = new AccountService();
            Account regAccount = accountService.createAccount(account);
            
            if (regAccount == null) {
                context.status(400);
            } else {
                context.json(mapper.writeValueAsString(regAccount));
            }
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}