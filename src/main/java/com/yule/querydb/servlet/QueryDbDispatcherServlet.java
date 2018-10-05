package com.yule.querydb.servlet;

import com.google.gson.Gson;
import com.yule.querydb.annotation.MyParam;
import com.yule.querydb.component.dbcomponent.service.DbComponentTopService;
import com.yule.querydb.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yule
 * @date 2018/10/1 14:32
 */
public class QueryDbDispatcherServlet extends HttpServlet {

    /**
     * 资源文件的主路径
     */
    private final String RESOURCE_PATH = "querydb/page";

    private final Logger logger = LoggerFactory.getLogger(QueryDbDispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        PropertiesUtils.init(config.getInitParameter("propertiesPath"));
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            doService(request, response);
        } catch (Exception e){
            logger.error("访问querydb报错！", e);
            returnErrorPage(response);
        }
    }

    private void doService(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        // root context
        if (contextPath == null) {
            contextPath = "";
        }
        // /querydb
//        String uri = contextPath + servletPath;
        // /index.html
        String path = requestURI.substring(contextPath.length() + servletPath.length());

        if(CommonUtil.isEmpty(path)){
            return;
        }

        setResponseContentType(response, path);

        response.setCharacterEncoding("utf-8");

        if (path.endsWith(".json")){
            //处理ajax请求
            dealJsonService(request, response, path);
        }else {
            //返回文件流  包含这种".html"
            responseFile(response, path);
        }
    }

    /**
     * 返回错误页面
     * @param response
     * @throws IOException
     */
    private void returnErrorPage(HttpServletResponse response) throws IOException {
        //不走后台了，以免循环重定向
        response.sendRedirect("/front/page/error.html");
    }

    /**
     * 返回404页面
     * @param response
     * @param path
     * @throws IOException
     */
    private void return404Page(HttpServletResponse response, String path) throws IOException {
        logger.error("404-找不到地址：{}", path);
        //不走后台了，以免循环重定向
        response.sendRedirect("/front/page/404.html");
    }

    /**
     * 返回文件路径
     * @param fileName
     * @return
     */
    private String getFilePath(String fileName) {
        return RESOURCE_PATH + fileName;
    }

    /**
     * 处理ajax的json请求
     * @param request
     * @param response
     * @param path
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void dealJsonService(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String methodName = path.substring(1, path.indexOf(".json"));

        final Method[] methods = DbComponentTopService.class.getMethods();
        boolean flag = false;
        for(Method method : methods){
            if(method.getName().equals(methodName)){
                flag = true;
                Object objResult = getObjResultByMethod(request, method);
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(objResult));
                break;
            }
        }

        if(!flag){
            return404Page(response, path);
        }
    }

    /**
     * 执行方法，获取结果值
     * @param request
     * @param method
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    private Object getObjResultByMethod(HttpServletRequest request, Method method) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object objResult;

        String methodName = method.getName();
        DbComponentTopService dbComponentTopService = SpringContextHolder.getBean(DbComponentTopService.class);
        //方法的参数类型
        final Class<?>[] parameterTypes = method.getParameterTypes();
        //存放方法的参数值
        final Object[] parameterValues = new Object[parameterTypes.length];
        int i = 0;

        if(CommonUtil.isNullOrBlock(parameterTypes)){
            Method myMethod = DbComponentTopService.class.getMethod(methodName);
            objResult = myMethod.invoke(dbComponentTopService);
        }else{
            //获取方法的参数注解
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (Annotation[] annotation1 : parameterAnnotations) {
                for (Annotation annotation : annotation1) {
                    if (annotation instanceof MyParam) {
                        MyParam customAnnotation = (MyParam) annotation;
                        Class curParameterType = parameterTypes[i];

                        if(curParameterType.getName().equals("java.lang.Integer")){
                            parameterValues[i++] = Integer.parseInt(request.getParameter(customAnnotation.value()));
                        }else{
                            parameterValues[i++] = request.getParameter(customAnnotation.value());
                        }
                    }
                }
            }

            Method myMethod = DbComponentTopService.class.getMethod(methodName, parameterTypes);
            objResult = myMethod.invoke(dbComponentTopService, parameterValues);
        }

        return objResult;
    }

    /**
     * 设置response的ContentType
     * @param response
     * @param path
     */
    private void setResponseContentType(HttpServletResponse response, String path) {
        if(!path.contains(".")){
            response.setContentType(ContentTypeUtil.getDefaultValue());
        }else{
            String suffix = path.substring(path.lastIndexOf("."));
            response.setContentType(ContentTypeUtil.getValueByKey(suffix));
        }
    }

    /**
     * 返回文件
     * @param response
     * @param path
     * @throws IOException
     */
    private void responseFile(HttpServletResponse response, String path) throws IOException {
        String filePath = getFilePath(path);
        String text = ResourceUtil.readFromResource(filePath);
        if(CommonUtil.isEmpty(text)){
            return404Page(response, path);
            return;
        }
        response.getWriter().write(text);
    }
}