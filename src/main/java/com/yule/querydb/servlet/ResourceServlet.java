package com.yule.querydb.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试用
 * @author yule
 * @date 2018/10/1 14:27
 */
public class ResourceServlet extends HttpServlet {

    public ResourceServlet(){}

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("访问了");
        super.service(req, resp);
    }
}
