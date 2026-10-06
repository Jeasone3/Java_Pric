package com.example.memo.dao;

import com.example.memo.model.User;
import com.example.memo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    // 先按用户名查账号，再比较普通密码。密码不去除首尾空格。
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT id, username, password FROM app_users WHERE username = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && password != null
                        && password.equals(resultSet.getString("password"))) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("username"));
                    return user;
                }
            }
        }
        return null;
    }
}
