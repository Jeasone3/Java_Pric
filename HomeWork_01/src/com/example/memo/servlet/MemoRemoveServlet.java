package com.example.memo.servlet;

import com.example.memo.dao.MemoDao;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 彻底删除只允许操作回收站里的记录。 */
public class MemoRemoveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = ServletUtil.getMemoId(request);
        if (id == -1) {
            ServletUtil.showError(request, response, 400, "备忘录编号无效。");
            return;
        }
        try {
            if (!new MemoDao().remove(id, ServletUtil.getUserId(request))) {
                ServletUtil.showError(request, response, 404, "回收站记录不存在，无法彻底删除。");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/memo/recycle");
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
