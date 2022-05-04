package connectFour.service;

import connectFour.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;



public class LoginServiceRestClient implements LoginService {
    private String url = "http://localhost:8080/api/user";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addUser(Login user) throws LoginException {
        restTemplate.postForEntity(url, user, Login.class);
    }

    @Override
    public Login getUser(String usrName, String password) throws LoginException {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }

    @Override
    public void reset() throws LoginException {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}


