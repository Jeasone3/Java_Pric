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

/** 保存详情页面提交的修改。 */
public class MemoUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = ServletUtil.getMemoId(request);
        if (id == -1) {
            ServletUtil.showError(request, response, 400, "备忘录编号无效。");
            return;
        }
        String title = ServletUtil.getTitle(request);
        String content = ServletUtil.getContent(request);
        request.setAttribute("title", title);
        request.setAttribute("content", content);
        try {
            MemoDao dao = new MemoDao();
            int userId = ServletUtil.getUserId(request);
            Memo memo = dao.findById(id, userId, 0);
            if (memo == null) {
                ServletUtil.showError(request, response, 404, "备忘录不存在，或已进入回收站。");
                return;
            }
            String error = ServletUtil.validateMemo(title, content);
            if (error != null) {
                request.setAttribute("memo", memo);
                request.setAttribute("error", error);
                request.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(request, response);
                return;
            }
            if (!dao.update(id, userId, title, content)) {
                ServletUtil.showError(request, response, 404, "保存失败，备忘录可能已被移入回收站。");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/memo/detail?id=" + id);
        } catch (SQLException e) {
            ServletUtil.showDatabaseError(request, response, e);
        }
    }
}
