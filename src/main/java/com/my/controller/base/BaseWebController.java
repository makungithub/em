package com.my.controller.base;

import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.my.common.util.RequestUtils;

/**
 * Created by makun
 */
public class BaseWebController {

    @Autowired
    private MessageSource  messageSource;

    @Resource
    private Properties     serverConstants;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public Properties getServerConstants() {
        return serverConstants;
    }

    public String getProfile(HttpServletRequest request) {
        return request.getServletContext().getInitParameter("spring.profiles.active");
    }

    public String getServerUrl(HttpServletRequest request, String key) {
        return getServerConstants().getProperty(MessageFormat.format("{0}.{1}", getProfile(request), key));
    }

    /**
     * Handle required parameter exception
     *
     * @param exception
     * @param request
     * @param response
     * @throws Exception
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected void handleMissingServletRequestParameterException(MissingServletRequestParameterException exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (RequestUtils.isJsonRequest(request)) {
            request.setAttribute("paramaterName", exception.getParameterName());
            RequestUtils.forward(request, response, "/system/handleMissingServletRequestParameterException");
        }
        else {
            RequestUtils.forward(request, response, "/system/error/500");

        }
    }

    /**
     * Handle exception
     *
     * @param exception
     * @param request
     * @param response
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    protected void handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (RequestUtils.isJsonRequest(request)) {
            request.setAttribute("exceptionDetails", ExceptionUtils.getStackTrace(exception));
            RequestUtils.forward(request, response, "/system/handleJsonException");
        }
        else {
            RequestUtils.forward(request, response, "/system/error/500");
        }

    }
}
