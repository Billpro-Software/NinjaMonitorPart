//package src.api;
//
//import com.bsl.infrawebserver.bean.*;
//import com.bsl.infrawebserver.email.EmailProcessor;
//import com.bsl.infrawebserver.bean.AuthenticationMapper;
//import com.bsl.infrawebserver.bean.AuthenticationBean;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.concurrent.atomic.AtomicLong;
//
//@RestController
//public class Authentication {
//
//    @Resource
//    AuthenticationMapper authenticationMapper;
//    @CrossOrigin(origins = "*")
//    @PostMapping("api/auth")
//    public AuthenticationBean  postAuth(@RequestBody AuthenticationBean authenticationBean)  {
//       return authenticationMapper.auth(authenticationBean);
//    }
//
//    @CrossOrigin(origins = "*")
//    @PatchMapping("api/update_profile")
//
//    public void  patchUpdate(@RequestBody AuthenticationBean authenticationBean )  {
//        authenticationMapper.updateProfile( authenticationBean );
//    }
//
//}
//
