package com.example.memo.util;

/** 把用户输入显示为普通文字，避免被浏览器当成 HTML。 */
public class HtmlUtil {
    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
