package jp.co.sss.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.repository.AgendaEntryRepository;

@Service
public class UserBeanTools {

    @Autowired
    AgendaBeanTools agendaBeanTools;

    @Autowired
    AgendaEntryRepository agendaEntryRepository;

    public UserBean copyEntityToBean(User entity) {
        UserBean userBean = new UserBean();
        BeanUtils.copyProperties(entity, userBean);
        
        userBean.setAgendaBeans(agendaBeanTools.copyEntityListToBeanList(agendaEntryRepository.findByUserAgendas(entity)));

        return userBean;
    }

    public List<UserBean> copyEntityListToBeanList(List<User> entityList) {
        List<UserBean> beanList = new ArrayList<UserBean>();

		for (User entity : entityList) {
			UserBean bean = new UserBean();
			BeanUtils.copyProperties(entity, bean);

            bean.setAgendaBeans(agendaBeanTools.copyEntityListToBeanList(agendaEntryRepository.findByUserAgendas(entity)));

			beanList.add(bean);
		}

		return beanList;
    }
}
