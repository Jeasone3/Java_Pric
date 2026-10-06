package com.example.memo.servlet;

import com.example.memo.dao.MemoDao;
import com.example.memo.model.Memo;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 详情页面同时提供修改表单。 */
public class MemoDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = ServletUtil.getMemoId(request);
        if (id == -1) {
            ServletUtil.showError(request, response, 400, "备忘录编号无效。");
            return;
        }
        try {
            Memo memo = new MemoDao().findById(id, ServletUtil.getUserId(request), 0);
            if (memo == null) {
                ServletUtil.showError(request, response, 404, "备忘录不存在，或已进入回收站。");
                return;
            }
            request.setAttribute("memo", memo);
            request.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(request, response);
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
