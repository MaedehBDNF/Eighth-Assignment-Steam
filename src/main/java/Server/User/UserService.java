package Server.User;

import Shared.Response;
import Shared.Enums.Error;
import Shared.Enums.Status;
import Shared.Enums.Title;
import Shared.Dto.LoginDto;
import Shared.Dto.RegisterDto;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class UserService {
    UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }

    public Response login(LoginDto data) {
        Response response = new Response();
        response.setTitle(Title.login);
        UserEntity foundUser = findByUsername(data.username);
        if (foundUser.getId().length() > 0){
            if (BCrypt.checkpw(data.password, foundUser.getPassword())){
                response.setStatus(Status.successful);
                foundUser.setPassword(null);
                response.setData(foundUser);
            } else {
                response.setError(Error.wrongPassword);
            }
        } else {
            response.setError(Error.wrongUsername);
        }
        return response;
    }

    public Response register(RegisterDto data) {
        Response response = new Response();
        response.setTitle(Title.register);
        UserEntity userEntity = new UserEntity();

        if (findByUsername(data.username).getDateOfBirth() != null) {
            response.setError(Error.duplicateUsername);
            return response;
        }

        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUsername(data.username);
        userEntity.setPassword(BCrypt.hashpw(data.password, BCrypt.gensalt()));
        userEntity.setDateOfBirth(data.dateOfBirth);
        if (!this.repository.insertIntoTable(userEntity)) {
            response.setError(Error.databaseError);
            return response;
        }
        response.setStatus(Status.successful);
        userEntity.setPassword(null);
        response.setData(userEntity);
        return response;
    }

    public Response logOut(){
        Response response = new Response();
        response.setTitle(Title.logOut);
        response.setStatus(Status.successful);
        return response;
    }

    private UserEntity findByUsername(String username){
        return this.repository.findByUsername(username);
    }
}
