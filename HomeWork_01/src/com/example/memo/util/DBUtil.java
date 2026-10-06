package com.example.memo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** 集中配置 JDBC 连接，每次数据库操作使用一个新连接。 */
public class DBUtil {
    // 请把默认用户名和密码改成自己的 MySQL 账号。
    // admin / student 是网页登录账号，不是这里的数据库账号。
    // 系统属性供运行时覆盖配置，日常学习直接修改默认值即可。
    private static final String URL = System.getProperty("memo.db.url",
            "jdbc:mysql://127.0.0.1:3306/memo_homework?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
    private static final String USERNAME = System.getProperty("memo.db.user", "root");
    private static final String PASSWORD = System.getProperty("memo.db.password", "mysql");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("找不到 MySQL JDBC 驱动，请检查 WEB-INF/lib。", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
