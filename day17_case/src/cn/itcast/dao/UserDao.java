package cn.itcast.dao;

import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User> findAll();
    public User login(User loginUser);
    public void andUser(User user);
    public void deleteID(int id);
    public User findID(int id);
    public void update(User user);


    int findTotalCount(Map<String, String[]> condition);

    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
