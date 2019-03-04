package ca.outercove.uomiapplication.backendCommunication;

import com.facebook.AccessToken;

import java.util.List;

public interface APICommunicator {

    /**
     * Creates a new user in the database.
     * @param
     * @return: true if user created, false otherwise
     */
    Object addNewUser(String email, String password, AccessToken token, String firstName, String lastName);

    /**
     * Retrieves a list with information about all users known to the application
     * @return: list with all users of the app
     */
    List getAllUsers();

    /**
     * Retrieves user information for specified user
     * @param userId: integer user id unique identifier
     * @return: object containing user information
     */
    Object getUserInfo(Integer userId);

    /**
     * Retrieves a list containing money-owing account information for the specified user
     * @param userId: integer user id unique identifier
     * @return: a list with all accounts related to the user
     */
    List getUserAccounts(Integer userId);

    /**
     * Will create a new account including the current user and at least one more user
     * @param userId: integer user id unique identifier
     * @param others: list of at least one more user
     * @return: information about the newly created account
     */
    Object addNewAccount(Integer userId, List<Integer> others);

    /**
     * Removes an account given an account id
     * @param accountId: account unique identifier
     * @return: true if account deleted, false otherwise
     */
    boolean removeAccount(Integer accountId);






}
