package jp.co.sss.management.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.UserBean;

@Service
public interface UserService {
    
    List<UserBean> showUserBeansByStatus();

    List<UserBean> searchUserBeansByUserId(List<Integer> userIdList);

}
