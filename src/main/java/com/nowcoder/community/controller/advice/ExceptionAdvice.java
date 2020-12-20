package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

//扫描所有带controller的bean
/*
ControllerAdvice支持的限定范围：

按注解：@ControllerAdvice(annotations = RestController.class)
按包名：@ControllerAdvice("org.example.controllers")
按类型：@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常" + e.getMessage());

        for (StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }

        String xRequestedWith = request.getHeader("x-requested-with");
        //判断请求是普通请求还是异步请求

        //如果是异步请求
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(CommunityUtil.getJOSNString(1, "服务器发生异常"));

        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
