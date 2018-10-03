package com.yule.querydb.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yule.querydb.annotation.MyParam;
import com.yule.querydb.component.dbcomponent.service.DbComponentTopService;
import com.yule.querydb.utils.CommonUtil;
import com.yule.querydb.utils.PropertiesUtils;
import com.yule.querydb.utils.ResourceUtil;
import com.yule.querydb.utils.SpringContextHolder;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author yule
 * @date 2018/10/1 14:32
 */
public class QueryDbDispatcherServlet extends HttpServlet {

    private final String INDEX_URL = "/index.html";

    /**
     * 资源文件的路径
     */
    private final String RESOURCE_PATH = "querydb/front";

    private static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>(){};

    private final Logger logger = LoggerFactory.getLogger(QueryDbDispatcherServlet.class);

    static {
        CONTENT_TYPE_MAP.put(".html", "text/html; charset=utf-8");
        CONTENT_TYPE_MAP.put(".css", "text/css;charset=utf-8");
        CONTENT_TYPE_MAP.put(".js", "text/javascript;charset=utf-8");
        CONTENT_TYPE_MAP.put(".json", "application/json");

        //字体
        CONTENT_TYPE_MAP.put(".ttf", "application/x-font-ttf");
        CONTENT_TYPE_MAP.put(".woff", "application/x-font-woff");

        //图片
        CONTENT_TYPE_MAP.put(".jpg", "image/jpeg");
        CONTENT_TYPE_MAP.put(".png", "image/png");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        PropertiesUtils.init(config.getInitParameter("propertiesPath"));
        super.init(config);
    }

    protected String getFilePath(String fileName) {
        return RESOURCE_PATH + fileName;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if(!path.contains(".")){
            return;
        }

        setResponseContentType(response, path);

        if (path.endsWith(".html")) {
            //返回主页面
            response.setCharacterEncoding("utf-8");
            dealHtmlService(response, path);
        }else if(path.endsWith(".json")){
            response.setCharacterEncoding("utf-8");
            try {
                dealJsonService(request, response, path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            responseFile(response, path);
        }
    }

    private void dealJsonService(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String methodName = path.substring(1, path.indexOf(".json"));

        DbComponentTopService dbComponentTopService = SpringContextHolder.getBean(DbComponentTopService.class);
        final Method[] methods = DbComponentTopService.class.getMethods();
        for(Method method : methods){
            if(method.getName().equals(methodName)){
                Object objResult;

                final Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameterValues = new Object[parameterTypes.length];
                int i = 0;

                if(CommonUtil.isNullOrBlock(parameterTypes)){
                    Method myMethod = DbComponentTopService.class.getMethod(methodName);
                    objResult = myMethod.invoke(dbComponentTopService);
                }else{
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
                Gson gson = new Gson();
                response.getWriter().print(gson.toJson(objResult));
            }
        }
    }

    /**
     * 设置response的ContentType
     * @param response
     * @param path
     */
    private void setResponseContentType(HttpServletResponse response, String path) {
        String suffix = path.substring(path.lastIndexOf("."));
        if(CommonUtil.isNotEmpth(suffix) && CONTENT_TYPE_MAP.containsKey(suffix)){
            response.setContentType(CONTENT_TYPE_MAP.get(suffix));
        } else {
            response.setContentType("application/octet-stream");
        }
    }

    /**
     * 处理html的service，直接返回 index.html
     * @param response
     * @param path
     * @throws IOException
     */
    private void dealHtmlService(HttpServletResponse response, String path) throws IOException {
        //只支持 index.html
        if(INDEX_URL.equals(path)){
            responseFile(response, path);
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
        response.getWriter().write(text);
    }
}
