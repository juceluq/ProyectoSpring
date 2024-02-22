package proyecto_spring.services;

import proyecto_spring.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);
}
