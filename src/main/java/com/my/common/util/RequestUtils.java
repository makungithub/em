package com.my.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UrlPathHelper;

/**
 * HttpServletRequest帮助类
 *
 * @author makun
 */
public class RequestUtils {

    private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

    public static boolean isJsonRequest(HttpServletRequest request) {
        final String requestedWith = request.getRequestURL().toString();
        return requestedWith.endsWith(".json");
    }

    public static boolean isAjaxRequest(WebRequest webRequest) {
        final String requestedWith = webRequest.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        final String requestedWith = request.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }

    public static boolean isAjaxUploadRequest(WebRequest webRequest) {
        return webRequest.getParameter("ajaxUpload") != null;
    }

    public static boolean isAjaxUploadRequest(HttpServletRequest request) {
        return request.getParameter("ajaxUpload") != null;
    }

    public static void forward(HttpServletRequest request, HttpServletResponse response, String url, Logger logger) throws ServletException, IOException {

        logger.info("Forward to " + request.getRequestURI());

        // Note: response.sendRedirect() will send a redirect status code to browser which will eventually make a NEW
        // request which is the reason that you are getting GET requests. This is also the reason behind not getting
        // POST data (as its a new and different request altogether). Use request.getRequestDispatcher() instead.
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        // forward happens on the server side, server forwards the request to another resource,
        // without the client being informed that a different resource is going to process the request.
        // This process occurs completely with in the web container. And forward takes the relative URL to the servlet.
        // Furthermore, using forward, one page can share the data stored in request to another one.
        // Interface javax.servlet.RequestDispatcher => void forward(ServletRequest request, ServletResponse response)
        dispatcher.forward(request, response);
    }

    public static void forward(ServletRequest request, ServletResponse response, String url) throws ServletException, IOException {
        // Note: response.sendRedirect() will send a redirect status code to browser which will eventually make a NEW
        // request which is the reason that you are getting GET requests. This is also the reason behind not getting
        // POST data (as its a new and different request altogether). Use request.getRequestDispatcher() instead.
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        // forward happens on the server side, server forwards the request to another resource,
        // without the client being informed that a different resource is going to process the request.
        // This process occurs completely with in the web container. And forward takes the relative URL to the servlet.
        // Furthermore, using forward, one page can share the data stored in request to another one.
        // Interface javax.servlet.RequestDispatcher => void forward(ServletRequest request, ServletResponse response)
        dispatcher.forward(request, response);
    }

    /**
     * 获取QueryString的参数，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的，
     * 那么将通过HttpServletRequest#getParameter获取。
     *
     * @param request
     *            web请求
     * @param name
     *            参数名称
     * @return
     */
    public static String getQueryParam(HttpServletRequest request, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        if (request.getMethod().equalsIgnoreCase("")) {
            return request.getParameter(name);
        }
        String s = request.getQueryString();
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            s = URLDecoder.decode(s, CharEncoding.UTF_8);
        }
        catch (UnsupportedEncodingException e) {
            log.error("encoding " + "uft-8" + " not support?", e);
        }
        String[] values = parseQueryString(s).get(name);
        if (values != null && values.length > 0) {
            return values[values.length - 1];
        }
        else {
            return null;
        }
    }

    public static Map<String, Object> getQueryParams(HttpServletRequest request) {
        Map<String, String[]> map;
        if (request.getMethod().equalsIgnoreCase("POST")) {
            map = request.getParameterMap();
        }
        else {
            String s = request.getQueryString();
            if (StringUtils.isBlank(s)) {
                return new HashMap<String, Object>();
            }
            try {
                s = URLDecoder.decode(s, CharEncoding.UTF_8);
            }
            catch (UnsupportedEncodingException e) {
                log.error("encoding uft-8" + " not support?", e);
            }
            map = parseQueryString(s);
        }

        Map<String, Object> params = new HashMap<String, Object>(map.size());
        int len;
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            len = entry.getValue().length;
            if (len == 1) {
                params.put(entry.getKey(), entry.getValue()[0]);
            }
            else if (len > 1) {
                params.put(entry.getKey(), entry.getValue());
            }
        }
        return params;
    }

    /**
     * Parses a query string passed from the client to the server and builds a
     * <code>HashTable</code> object with key-value pairs. The query string
     * should be in the form of a string packaged by the GET or POST method,
     * that is, it should have key-value pairs in the form <i>key=value</i>,
     * with each pair separated from the next by a &amp; character.
     * <p/>
     * <p/>
     * A key can appear more than once in the query string with different
     * values. However, the key appears only once in the hashtable, with its
     * value being an array of strings containing the multiple values sent by
     * the query string.
     * <p/>
     * <p/>
     * The keys and values in the hashtable are stored in their decoded form, so
     * any + characters are converted to spaces, and characters sent in
     * hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
     *
     * @param s
     *            a string containing the query to be parsed
     * @return a <code>HashTable</code> object built from the parsed key-value
     *         pairs
     * @throws IllegalArgumentException
     *             if the query string is invalid
     */
    public static Map<String, String[]> parseQueryString(String s) {
        String valArray[] = null;
        if (s == null) {
            throw new IllegalArgumentException();
        }
        Map<String, String[]> ht = new HashMap<String, String[]>();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = (String) st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = pair.substring(0, pos);
            String val = pair.substring(pos + 1, pair.length());
            if (ht.containsKey(key)) {
                String oldVals[] = (String[]) ht.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0 ; i < oldVals.length ; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = val;
            }
            else {
                valArray = new String[1];
                valArray[0] = val;
            }
            ht.put(key, valArray);
        }
        return ht;
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request, String prefix) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        String name;
        while (names.hasMoreElements()) {
            name = names.nextElement();
            if (name.startsWith(prefix)) {
                request.getParameterValues(name);
                map.put(name.substring(prefix.length()), StringUtils.join(request.getParameterValues(name), ','));
            }
        }
        return map;
    }

    /**
     * 获取真实发出请求的客户端IP
     * <p/>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法获得的是nginx的地址，并非真实发出请求的客户端IP
     * <p/>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用逗号分割)，如果还不存在则调用Request.getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getRealClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            else {
                return ip;
            }
        }
        else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获得当的访问路径
     * <p/>
     * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
     *
     * @param request
     * @return
     */
    public static String getLocation(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        StringBuffer buff = request.getRequestURL();
        String uri = request.getRequestURI();
        String origUri = helper.getOriginatingRequestUri(request);
        buff.replace(buff.length() - uri.length(), buff.length(), origUri);
        String queryString = helper.getOriginatingQueryString(request);
        if (queryString != null) {
            buff.append("?").append(queryString);
        }
        return buff.toString();
    }

    /**
     * 获得请求的session id，但是HttpServletRequest#getRequestedSessionId()方法有一些问题。
     * 当存在部署路径的时候，会获取到根路径下的jsessionid。
     *
     * @param request
     * @return
     * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
     */
    public static String getRequestedSessionId(HttpServletRequest request) {
        String sessionId = request.getRequestedSessionId();
        String contextPath = request.getContextPath();
        // 如果session id是从url中获取，或者部署路径为空，那么是在正确的。
        if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(contextPath)) {
            return sessionId;
        }
        else {
            // 手动从cookie获取
            Cookie cookie = CookieUtils.getCookie(request, "JSESSIONID");
            if (cookie != null) {
                return cookie.getValue();
            }
            else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
    }
}
