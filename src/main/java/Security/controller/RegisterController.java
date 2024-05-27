package Security.controller;

import Security.model.MyUser;
import Security.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
   private PasswordEncoder passwordEncoder;
    @Autowired
    private MyUserRepo userRepo;

    @GetMapping("/user")

    public MyUser createUser(@RequestBody MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
