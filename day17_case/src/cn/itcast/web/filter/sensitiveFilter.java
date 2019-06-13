package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/*
* 敏感词汇过滤
* */
@WebFilter("/*")
public class sensitiveFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //创建代理对象，增强getParament方法
        ServletRequest proxy_req = (ServletRequest) Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强getParament方法
                if (method.getName().equals("getParameter")){
                    //增强返回值
                    //获取返回值
                    String value = (String) method.invoke(req,args);

                }

                return method.invoke(req,args);
            }
        });

        chain.doFilter( proxy_req, resp);
    }
private List<String>list = new ArrayList<String>();//敏感词汇集合
    public void init(FilterConfig config) throws ServletException {
        try {
            //在过滤器启动时，加载敏感词汇sensitive.txt表，得到敏感词汇集合
            list = new ArrayList<String>();
            //解析sensitive.txt
            String realPath = config.getServletContext().getRealPath("/WEB-INF/classes/sensitive.txt");
            //如果sensitive.txt的字符集是UTF-8，就需要在解析时指定字符集
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(realPath), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

        public void destroy() {
        }

    }