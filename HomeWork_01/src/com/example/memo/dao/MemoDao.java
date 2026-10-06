package com.example.memo.dao;

import com.example.memo.model.Memo;
import com.example.memo.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemoDao {
    // 正常列表传入 0，回收站列表传入 1。
    public List<Memo> list(int userId, int status) throws SQLException {
        String sql = "SELECT id, user_id, title, content, status, created_at, updated_at "
                + "FROM memos WHERE user_id = ? AND status = ? ORDER BY updated_at DESC, id DESC";
        List<Memo> memos = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setInt(2, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    memos.add(readMemo(resultSet));
                }
            }
        }
        return memos;
    }

    // 编号、所属用户和状态必须同时匹配，不能查看其他用户的记录。
    public Memo findById(int id, int userId, int status) throws SQLException {
        String sql = "SELECT id, user_id, title, content, status, created_at, updated_at "
                + "FROM memos WHERE id = ? AND user_id = ? AND status = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.setInt(3, status);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return readMemo(resultSet);
                }
            }
        }
        return null;
    }

    // 新增记录的时间由数据库默认值填写。
    public boolean add(int userId, String title, String content) throws SQLException {
        String sql = "INSERT INTO memos (user_id, title, content, status) VALUES (?, ?, ?, 0)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, title);
            statement.setString(3, content);
            return statement.executeUpdate() == 1;
        }
    }

    // 回收站中的记录不能直接修改。
    public boolean update(int id, int userId, String title, String content) throws SQLException {
        String sql = "UPDATE memos SET title = ?, content = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE id = ? AND user_id = ? AND status = 0";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, id);
            statement.setInt(4, userId);
            return statement.executeUpdate() == 1;
        }
    }

    // 普通删除只修改状态，记录仍保留在数据库中。
    public boolean moveToRecycleBin(int id, int userId) throws SQLException {
        String sql = "UPDATE memos SET status = 1, updated_at = CURRENT_TIMESTAMP "
                + "WHERE id = ? AND user_id = ? AND status = 0";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            return statement.executeUpdate() == 1;
        }
    }

    // 只有回收站中的记录可以还原。
    public boolean restore(int id, int userId) throws SQLException {
        String sql = "UPDATE memos SET status = 0, updated_at = CURRENT_TIMESTAMP "
                + "WHERE id = ? AND user_id = ? AND status = 1";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            return statement.executeUpdate() == 1;
        }
    }

    // 彻底删除必须限定回收站状态。
    public boolean remove(int id, int userId) throws SQLException {
        String sql = "DELETE FROM memos WHERE id = ? AND user_id = ? AND status = 1";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            return statement.executeUpdate() == 1;
        }
    }

    // 将数据库中的一行结果转换成普通 Java 对象。
    private Memo readMemo(ResultSet resultSet) throws SQLException {
        Memo memo = new Memo();
        memo.setId(resultSet.getInt("id"));
        memo.setUserId(resultSet.getInt("user_id"));
        memo.setTitle(resultSet.getString("title"));
        memo.setContent(resultSet.getString("content"));
        memo.setStatus(resultSet.getInt("status"));
        memo.setCreatedAt(resultSet.getTimestamp("created_at"));
        memo.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        return memo;
    }
}
