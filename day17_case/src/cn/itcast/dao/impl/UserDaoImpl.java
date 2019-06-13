package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate temp = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        String sql = "select * from user";
        List<User> users = temp.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User login(User loginUser) {


        try {
            String sql = "select *from user where username =? and password =?";
            User user = temp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), loginUser.getUsername(), loginUser.getPassword());
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void andUser(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        temp.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void deleteID(int id) {
        String sql = "delete from user where id = ?";
        temp.update(sql, id);
    }

    @Override
    public User findID(int id) {
        String sql = "select * from user where id = ? ";
        User user = temp.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;

    }

    @Override
    public void update(User user) {
        String sql = "update user set name=?,gender=?,age=?,address=?,qq=?,email=? where id = ?";
        temp.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        Set<String> keySet = condition.keySet();
        //参数集合
        List<Object> parmas = new ArrayList<Object>();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            String value = condition.get(key)[0];

            if (value != null && !"".equals(value)) {
                sb.append(" and " +key+ " like ? ");
                parmas.add( "%" +value+ "%");//条件值
            }
        }
       sql = sb.toString();
        return temp.queryForObject(sql, Integer.class, parmas.toArray());

    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        Set<String> keySet = condition.keySet();
        //参数集合
        List<Object> parmas = new ArrayList<Object>();
        for (String key : keySet) {
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }

            String value = condition.get(key)[0];

            if (value != null && !"".equals(value)) {
                sb.append(" and " + key + " like ? ");
                parmas.add( "%" + value + "%" );//条件值
            }
        }
        //添加分页查询
        sb.append(" limit ?,? ");
        parmas.add(start);
        parmas.add(rows);
         sql = sb.toString();
        return temp.query(sql, new BeanPropertyRowMapper<User>(User.class),parmas.toArray());

    }


}

