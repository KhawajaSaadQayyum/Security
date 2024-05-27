package Security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController ("/register")
public class ContentController {
    @GetMapping("/home")
    public String handleWelcome(){
        return "Home";
    }
    @GetMapping("/admin/home")
    public String handleAdminHome(){
        return "Home_Admin";
    }
    @GetMapping("/user/home")
    public String handleUserHome(){
        return "Home_User";
    }
    @GetMapping("/login")
    public String handleLogin(){
        return "custom_login";
    }
}
