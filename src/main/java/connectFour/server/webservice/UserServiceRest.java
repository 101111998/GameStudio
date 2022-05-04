package connectFour.server.webservice;

import connectFour.entity.Login;
import connectFour.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserServiceRest {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public void addUser(@RequestBody Login usr){
        loginService.addUser(usr);
    }
}
