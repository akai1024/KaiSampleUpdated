package kai.sample.controller;

import kai.sample.controller.msg.BasicResponse;
import kai.sample.controller.msg.RegisterRequest;
import kai.sample.controller.msg.ResponseBuilder;
import kai.sample.datasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register")
    public BasicResponse getChat(@RequestBody RegisterRequest request) {
        return ResponseBuilder.newResponse(userService.createUser(request));
    }

}
