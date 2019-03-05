package ca.outercove.uomiapplication.backendCommunication;

import com.facebook.AccessToken;

import java.util.List;

public class UomiAPICommunicator implements APICommunicator {
    @Override
    public Object addNewUser(String email, String password, AccessToken token, String firstName, String lastName) {
        return null;
    }

    @Override
    public List getAllUsers() {
        return null;
    }

    @Override
    public Object getUserInfo(Integer userId) {
        return null;
    }

    @Override
    public List getUserAccounts(Integer userId) {
        return null;
    }

    @Override
    public Object addNewAccount(Integer userId, List<Integer> others) {
        return null;
    }

    @Override
    public boolean removeAccount(Integer accountId) {
        return false;
    }

    @Override
    public Object addTransactionItem(Object transactionItem, Integer accountId) {
        return null;
    }

    @Override
    public boolean removeUser(Integer userId) {
        return false;
    }

    @Override
    public Double getUserBalance(Integer userId) {
        return null;
    }

    @Override
    public List getTransactions(Integer accountId) {
        return null;
    }

    @Override
    public boolean removeTransaction(Integer transactionId) {
        return false;
    }
}
