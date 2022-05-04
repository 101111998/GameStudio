package connectFour.server.controller;

import connectFour.entity.Login;
import connectFour.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    private Login loggedUser;
    @Autowired
    private LoginService loginService;

    @RequestMapping("/registration")
    public String registration(String login, String password){
        loginService.addUser(new Login(login, password, new Date()));
        return "registration";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
        loggedUser = loginService.getUser(login, password);
        return "redirect:/connectfour";
    }

    @RequestMapping("/logout")
    public String logout(){
        loggedUser = null;
        return "redirect:/";
    }

    public Login getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged(){
        return loggedUser != null;
    }
}
