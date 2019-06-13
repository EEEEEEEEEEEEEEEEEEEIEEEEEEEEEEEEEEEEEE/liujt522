package cn.itcast.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强制转换
        HttpServletRequest request = (HttpServletRequest) req;
        //获取资源请求路径
        String uri = request.getRequestURI();
        //判断是否包含登陆相关资源路径  css、js、图片、验证码等等.
        if (uri.contains("/login.jsp")|| uri.contains("/loginServlet")||uri.contains("/checkCodeServlet")||uri.contains("/css/")||uri.contains("/js/")||uri.contains("/fonts/")){
        //包含，放行
            chain.doFilter(req, resp);
    }else {
            //不包含，验证是否登陆
            //从Session 中获取user
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");
            if(user != null){
                //已经登陆，放行
                chain.doFilter(req, resp);
            }else {
                request.setAttribute("login_error","您尚未登陆，请登陆");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }
}

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
