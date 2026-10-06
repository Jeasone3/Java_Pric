package com.example.memo.servlet;

import com.example.memo.dao.MemoDao;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 主页只显示当前用户的正常备忘录。 */
public class MemoListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("memos", new MemoDao().list(ServletUtil.getUserId(request), 0));
            request.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(request, response);
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
