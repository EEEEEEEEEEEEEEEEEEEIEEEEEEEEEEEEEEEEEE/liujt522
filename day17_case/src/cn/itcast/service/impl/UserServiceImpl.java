package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao =  new UserDaoImpl();
    /*
    * 查询
    * */
    @Override
    public List<User> findAll() {
        return dao.findAll();
    }
    /*
    * 登陆
    * */
    @Override
    public User login(User loginUser) {
        return dao.login(loginUser);

    }
    /*
    *
    * 添加
    * */
    @Override
    public void andUser(User user) {
        dao.andUser(user);
    }

    /*
    * 根据id删除
    * */
    @Override
    public void deleteId(String id) {
        dao.deleteID(Integer.parseInt(id));
    }


    /*
    * 修改*/
    @Override
    public User findID(String id) {

        return dao.findID(Integer.parseInt(id));
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }

    @Override
    public void delServletById(String[] uids) {
        if (uids != null || uids.length > 0) {
            for (String uid : uids) {
                dao.deleteID(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
       int currentPage = Integer.parseInt(_currentPage);
       int rows = Integer.parseInt(_rows);
        if (currentPage<1){
currentPage =1;
        }
        PageBean<User> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        int start = (currentPage - 1 )* rows;
        List<User>list = dao.findByPage(start,rows,condition);
        pb.setList(list);
        int totalPage =totalCount % rows == 0 ? (totalCount / rows) : (totalCount / rows) +1;
       pb.setTotalPage(totalPage);
        return pb;

        }
}
