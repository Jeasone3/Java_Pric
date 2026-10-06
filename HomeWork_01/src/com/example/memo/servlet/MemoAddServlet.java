package com.example.memo.servlet;

import com.example.memo.dao.MemoDao;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 打开发布页，检查输入后保存备忘录。 */
public class MemoAddServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = ServletUtil.getTitle(request);
        String content = ServletUtil.getContent(request);
        request.setAttribute("title", title);
        request.setAttribute("content", content);
        String error = ServletUtil.validateMemo(title, content);
        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(request, response);
            return;
        }
        try {
            if (!new MemoDao().add(ServletUtil.getUserId(request), title, content)) {
                ServletUtil.showError(request, response, 500, "发布失败，请稍后重试。");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/memo/list");
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
