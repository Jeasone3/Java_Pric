package com.example.memo.servlet;

import com.example.memo.dao.MemoDao;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 回收站只显示当前用户已经删除的记录。 */
public class RecycleBinServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("memos", new MemoDao().list(ServletUtil.getUserId(request), 1));
            request.getRequestDispatcher("/WEB-INF/views/recycle.jsp").forward(request, response);
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
