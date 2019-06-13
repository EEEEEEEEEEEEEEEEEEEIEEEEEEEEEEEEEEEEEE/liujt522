package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> findAll();
    public User login(User loginUser);
    public void andUser(User user);
    public void deleteId(String id);
    public User findID(String id);
    public void update(User user);

    void delServletById(String[] uids);


    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);

}
