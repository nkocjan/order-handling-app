package pl.nkocjan.order_handling_app.repository;

import org.openapitools.model.User;
import org.openapitools.model.UserCreateRequest;
import org.openapitools.model.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.nkocjan.order_handling_app.service.CRUDInterface;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository implements CRUDInterface<User, UserCreateRequest, UserUpdateRequest> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll() {
        return jdbcTemplate.query("select * from user",
                BeanPropertyRowMapper.newInstance(User.class));
    }


    public User create(UserCreateRequest request) {
        UUID uuid = UUID.randomUUID();
        String sql = "insert into user (id,login,password, email) values (?,?,?,?)";

        jdbcTemplate.update(sql, uuid.toString(), request.getLogin(), request.getPassword(), request.getEmail());

        return new User(uuid.toString(), request.getLogin(), request.getPassword(), request.getEmail());
    }


    public User update(UserUpdateRequest request) {
        String sql = "update user set login=?,password=?,email=? where id=?";

        jdbcTemplate.update(sql, request.getLogin(), request.getPassword(), request.getEmail(), request.getId());

        return new User(request.getId(), request.getLogin(), request.getPassword(), request.getEmail());
    }

    public int delete(UUID id) {
        return jdbcTemplate.update("delete from user where id=?", id.toString());
    }

    public User getById(UUID id) {
        String sql = "select * from user where id=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id.toString());
    }

    public boolean existsByLogin(String login) {
        String sql = "select count(*) from user where login=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, login);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "select count(*) from user where email=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
