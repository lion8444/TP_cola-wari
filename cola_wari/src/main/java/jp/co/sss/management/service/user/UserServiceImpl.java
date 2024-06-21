package jp.co.sss.management.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.repository.AgendaEntryRepository;
import jp.co.sss.management.repository.UserRepository;
import jp.co.sss.management.service.AgendaBeanTools;
import jp.co.sss.management.service.UserBeanTools;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AgendaEntryRepository agendaEntryRepository;

    @Autowired
    AgendaBeanTools agendaBeanTools;

    @Autowired
    UserBeanTools userBeanTools;

    @Override
    public List<UserBean> showUserBeansByStatus() {
        
        List<User> user = userRepository.findByStatus(0);

        List<UserBean> userBeans = userBeanTools.copyEntityListToBeanList(user);

        for (UserBean userBean : userBeans) {
            User tempUser = new User();
            tempUser.setUserId(userBean.getUserId());
            
            List<AgendaBean> agendaBeans = agendaBeanTools.copyEntityListToBeanList(agendaEntryRepository.findByUserAgendas(tempUser));

            userBean.setAgendaBeans(agendaBeans);
        }

        return userBeans;
        
    }

    @Override
    public List<UserBean> searchUserBeansByUserId(List<Integer> userIdList) {
        List<UserBean> userBeans = new ArrayList<>();

        for (Integer userId : userIdList) {
            log.debug("UserServiceImpl.searchUserBeansByUserId userIdList Check : {}", userId);
            User user = userRepository.getReferenceById(userId);
            UserBean userBean = userBeanTools.copyEntityToBean(user);

            List<AgendaBean> agendaBeans = agendaBeanTools.copyEntityListToBeanList(agendaEntryRepository.findByUserAgendas(user));
            if(!agendaBeans.isEmpty()) {
                log.debug("UserServiceImpl.searchUserBeansByUserId agendaBean Check : {}", agendaBeans.get(0).toString());
            }
            
            userBean.setAgendaBeans(agendaBeans);

            userBeans.add(userBean);
        }
        return userBeans;
    }
    
}
