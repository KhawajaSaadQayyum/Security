package Security.model;

import Security.repo.MyUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private MyUserRepo myUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user=  myUserRepo.findByUserName(username);
        if(user.isPresent()){
           var userObj = user.get();
           return User.builder()
                    .username(userObj.getUserName())
                    .password(userObj.getPassword())
                    .roles(getRole(userObj))
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }

    }
    private String [] getRole(MyUser user){
        if(user.getRole()==null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
