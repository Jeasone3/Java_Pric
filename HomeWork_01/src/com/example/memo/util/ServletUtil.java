package com.example.memo.util;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 几个 Servlet 共用的参数检查和错误提示。 */
public class ServletUtil {
    public static int getUserId(HttpServletRequest request) {
        // LoginFilter 已经检查登录，用户编号只从 Session 中取。
        return (Integer) request.getSession(false).getAttribute("userId");
    }

    public static int getMemoId(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            return id > 0 ? id : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getTitle(HttpServletRequest request) {
        String title = request.getParameter("title");
        return title == null ? "" : title.trim();
    }

    public static String getContent(HttpServletRequest request) {
        String content = request.getParameter("content");
        return content == null ? "" : content;
    }

    public static String validateMemo(String title, String content) {
        if (title.length() == 0 || title.length() > 120) {
            return "标题不能为空，且最多填写 120 个字符。";
        }
        if (content.trim().length() == 0) {
            return "正文不能为空。";
        }
        if (content.length() > 10000) {
            return "正文最多填写 10000 个字符。";
        }
        return null;
    }

    public static void showError(HttpServletRequest request, HttpServletResponse response,
                                 int status, String message) throws ServletException, IOException {
        response.setStatus(status);
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }

    public static void showDatabaseError(HttpServletRequest request, HttpServletResponse response,
                                         SQLException exception) throws ServletException, IOException {
        request.getServletContext().log("数据库操作失败：" + request.getServletPath(), exception);
        showError(request, response, 500, "数据库操作失败，请检查 MySQL 是否启动以及数据库连接配置。");
    }
}
