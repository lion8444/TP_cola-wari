package jp.co.sss.management.service.agenda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AgendaEntryRepository agendaEntryRepository;

    @Autowired
    AgendaBeanTools agendaBeanTools;

    @Autowired
    UserBeanTools userBeanTools;

    @Override
    public List<AgendaBean> showAgendaBeansWithUsers(List<UserBean> userBeans) {

        Set<AgendaBean> agendaBeans = new HashSet<>();

        List<AgendaBean> temps = new ArrayList<>();

        if (userBeans.size() == 1) {
            temps = userBeans.get(0).getAgendaBeans();
            return temps;
        }
        
        for (UserBean userBean : userBeans) {
            temps.addAll(userBean.getAgendaBeans());
        }
        for (AgendaBean temp : temps) {
            if (temps.indexOf(temp) != temps.lastIndexOf(temp)) {
                agendaBeans.add(temp);
                log.debug("AgendaServiceImpl.showAgendaBeansWithUsers AgendaBean Check : {}", temp.toString());
            }
        }
        List<AgendaBean> result = new ArrayList<>(agendaBeans);

        return result;
        
    }
    
}
