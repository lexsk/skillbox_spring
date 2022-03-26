package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements ProjectRepository<User> {

    private final Logger logger = Logger.getLogger(UserRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> retrieveAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users", (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        });
        return new ArrayList<>(users);
    }

    @Override
    public void store(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", user.getUsername());
        parameterSource.addValue("password", user.getPassword());
        jdbcTemplate.update("INSERT INTO users (username, password) VALUES (:username, :password)", parameterSource);
        logger.info("store new user: " + user.getUsername());
    }

    @Override
    public boolean removeItemById(Integer userIdToRemove) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("id", userIdToRemove);
            jdbcTemplate.update("DELETE FROM users WHERE id = :id", parameterSource);
            logger.info("remove user completed");
            return true;
        } catch (Exception e) {
            logger.error("removeItemById ERROR", e);
            return false;
        }
    }
}
