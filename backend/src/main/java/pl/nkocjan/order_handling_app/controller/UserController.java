package pl.nkocjan.order_handling_app.controller;

import org.openapitools.model.User;
import org.openapitools.model.UserCreateRequest;
import org.openapitools.model.UserUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.nkocjan.order_handling_app.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController implements CRUDControllerInterface<User, UserCreateRequest, UserUpdateRequest> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserCreateRequest user) {
        User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping
    public ResponseEntity<User> update(@RequestBody UserUpdateRequest user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(id));
    }


}
