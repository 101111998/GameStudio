package connectFour.service;

import connectFour.entity.Login;

public interface LoginService {
    void addUser(Login usr) throws LoginException;
    Login getUser(String usrName, String password) throws LoginException;
    void reset() throws LoginException;
}
