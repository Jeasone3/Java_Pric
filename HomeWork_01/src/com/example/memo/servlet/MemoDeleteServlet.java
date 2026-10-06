package com.example.memo.servlet;

import com.example.memo.dao.MemoDao;
import com.example.memo.util.ServletUtil;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 普通删除只把正常记录移入回收站。 */
public class MemoDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = ServletUtil.getMemoId(request);
        if (id == -1) {
            ServletUtil.showError(request, response, 400, "备忘录编号无效。");
            return;
        }
        try {
            if (!new MemoDao().moveToRecycleBin(id, ServletUtil.getUserId(request))) {
                ServletUtil.showError(request, response, 404, "备忘录不存在，或已进入回收站。");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/memo/list");
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
