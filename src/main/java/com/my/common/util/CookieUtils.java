package com.my.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

/**
 * Cookie 辅助类
 *
 * @author makun
 */
public class CookieUtils {

    /**
     * 每页条数cookie名称
     */
    public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
    /**
     * 默认每页条数
     */
    public static final int    DEFAULT_SIZE     = 20;
    /**
     * 最大每页条数
     */
    public static final int    MAX_SIZE         = 200;

    /**
     * 获得cookie的每页条数
     * <p/>
     * 使用_cookie_page_size作为cookie name
     *
     * @param request
     *            HttpServletRequest
     * @return default:20 max:200
     */
    public static int getPageSize(HttpServletRequest request) {
        Cookie cookie = getCookie(request, COOKIE_PAGE_SIZE);
        int count = 0;
        if (cookie != null) {
            try {
                count = Integer.parseInt(cookie.getValue());
            }
            catch (Exception e) {
            }
        }
        if (count <= 0) {
            count = DEFAULT_SIZE;
        }
        else if (count > MAX_SIZE) {
            count = MAX_SIZE;
        }
        return count;
    }

    /**
     * 获得cookie
     *
     * @param request
     *            HttpServletRequest
     * @param name
     *            cookie name
     * @return if exist return cookie, else return null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        return WebUtils.getCookie(request, name);
    }

    /**
     * 根据部署路径，将cookie保存在根目录。
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param expiry
     * @return
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer expiry) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieName(name);
        String ctx = request.getContextPath();
        cookieGenerator.setCookiePath(StringUtils.isBlank(ctx) ? "/" : ctx);
        cookieGenerator.setCookieMaxAge(expiry);
        cookieGenerator.addCookie(response, value);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.setCookieName(name);
        String ctx = request.getContextPath();
        cookieGenerator.setCookiePath(StringUtils.isBlank(ctx) ? "/" : ctx);
        cookieGenerator.removeCookie(response);
    }
}