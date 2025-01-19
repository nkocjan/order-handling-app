package pl.nkocjan.order_handling_app.service;

import org.openapitools.model.User;
import org.openapitools.model.UserCreateRequest;
import org.openapitools.model.UserUpdateRequest;
import org.springframework.stereotype.Service;
import pl.nkocjan.order_handling_app.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService implements CRUDInterface<User, UserCreateRequest, UserUpdateRequest> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserCreateRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("Login already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        validLogin(request.getLogin());
        validPassword(request.getPassword());
        validEmail(request.getEmail());
        return userRepository.create(request);
    }

    public User update(UserUpdateRequest request) {
        userRepository.getById(UUID.fromString(request.getId()));
        validLogin(request.getLogin());
        validPassword(request.getPassword());
        validEmail(request.getEmail());
        return userRepository.update(request);
    }

    public int delete(UUID id) {
        int rowsAffected = userRepository.delete(id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("User not found");
        }
        return rowsAffected;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(UUID id) {
        return userRepository.getById(id);
    }

    public void validEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    ;

    public void validLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null");
        }
        if (login.length() < 3 || login.length() > 20) {
            throw new IllegalArgumentException("Login length must be between 3 and 20 characters");
        }
    }

    public void validPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        if (password.length() < 8 || password.length() > 40) {
            throw new IllegalArgumentException("Password length must be between 8 and 40 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }
    }


}
