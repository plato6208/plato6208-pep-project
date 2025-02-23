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
        app.get("/login", this::login);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void login(Context context) {
        try {
            // Parse JSON input to Account object
            Account accountInput = context.bodyAsClass(Account.class);

            // Verify user credentials
            Account account = accountService.getAccount(accountInput.getUsername(), accountInput.getPassword());
            
            // If credentials match, return account details with a 200 status code
            if (account != null) {
                context.status(200).json(account);
            } else {
                // If credentials don't match, return a 401 Unauthorized error
                context.status(401).result("Invalid username or password");
            }
        } catch (Exception e) {
            // If an error occurs during login, return 401 with an error message
            context.status(401).result("Error during login: " + e.getMessage());
        }
    }


}