package cn.itcast.web.servlet;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("utf-8");
        String verifycode = request.getParameter("verifycode");

        Map<String, String[]> parameterMap = request.getParameterMap();
        //创建User对象
        User loginuser= new User();
        //使用BeanUtils封装
        try {
            BeanUtils.populate(loginuser,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        User user = service.login(loginuser);

        //3.先获取生成的验证码
        HttpSession session = request.getSession();
        String checkCode_session = (String) session.getAttribute("CHECKCODE_SERVER");
        //4.删除session中存储的验证码
        session.removeAttribute("checkCode_session");
        //4.1先判断验证码是否正确
        //忽略大小写比较
        //验证码正确
        //判断用户名和密码是否一致
        if (checkCode_session !=null &&checkCode_session.equalsIgnoreCase(verifycode)){
            if (user ==null){
                //登录失败
                //存储提示信息到request
                request.setAttribute("login_error","用户名或密码错误");
                //转发到登录页面
                request.getRequestDispatcher("/login.jsp").forward(request,response);

            }else {
                //登录成功
                //储存信息
                session.setAttribute("user",user);
                //重定向到success.jsp
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }else {
            //验证码不一致
            //存储提示信息到request
            request.setAttribute("login_error","验证码错误");
            //转发到登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }






    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
