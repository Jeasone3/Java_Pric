package com.example.memo.servlet;

import com.example.memo.dao.UserDao;
import com.example.memo.model.User;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 打开登录页并处理登录表单。 */
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            response.sendRedirect(request.getContextPath() + "/memo/list");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        username = username == null ? "" : username.trim();
        request.setAttribute("username", username);
        if (username.length() == 0 || username.length() > 50 || password == null
                || password.length() == 0 || password.length() > 255) {
            request.setAttribute("error", "请输入有效的用户名和密码。");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        try {
            User user = new UserDao().login(username, password);
            if (user == null) {
                request.setAttribute("error", "用户名或密码错误。");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }
            // 登录成功后建立新会话，Session 只保存编号和用户名。
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            response.sendRedirect(request.getContextPath() + "/memo/list");
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
