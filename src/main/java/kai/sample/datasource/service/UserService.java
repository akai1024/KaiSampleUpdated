package kai.sample.datasource.service;

import kai.sample.controller.msg.ErrorCode;
import kai.sample.controller.msg.RegisterRequest;
import kai.sample.datasource.User;
import kai.sample.datasource.UserRepository;
import kai.sample.util.KaiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    public static final int MIN_USER_SIZE = 1;
    public static final int MAX_USER_SIZE = 12;
    public static final int MIN_PWD_SIZE = 4;
    public static final int MAX_PWD_SIZE = 12;

    @Autowired
    private UserRepository userRepo;

    /**
     * 檢查user的密碼是否正確
     */
    public boolean isPasswordCorrect(String user, String password) {
        Optional<User> opUser = userRepo.findById(user);
        if (opUser.isPresent()) {
            return KaiUtil.strEquals(opUser.get().getPassword(), password);
        }
        return false;
    }

    /**
     * 建立新user
     */
    public ErrorCode createUser(RegisterRequest request) {
        if (request == null) {
            return ErrorCode.ILLEGAL_PARAMETERS;
        }

        String user = request.getUser();
        String password = request.getPwd();
        if (KaiUtil.isEmptyStr(user) || KaiUtil.isEmptyStr(password)) {
            return ErrorCode.ILLEGAL_PARAMETERS;
        }

        if (user.length() < MIN_USER_SIZE || user.length() > MAX_USER_SIZE) {
            return ErrorCode.ILLEGAL_USER;
        }

        if (password.length() < MIN_PWD_SIZE || password.length() > MAX_PWD_SIZE) {
            return ErrorCode.ILLEGAL_PWD;
        }

        if (userRepo.existsById(user)) {
            return ErrorCode.USER_EXISTS;
        }

        User newUser = new User();
        newUser.setAccount(user);
        newUser.setPassword(password);
        newUser.setCreate_time(new Date());
        userRepo.save(newUser);
        return ErrorCode.SUCCESS;
    }


}
