package jp.co.sss.management.service.agenda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.bean.UserBean;
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

        Set<AgendaBean> agendaBeanSet = new HashSet<>();

        List<AgendaBean> temps = new ArrayList<>();

       if (userBeans.size() == 1) {
           temps = userBeans.get(0).getAgendaBeans();
           return temps;
       }
        
        for (UserBean userBean : userBeans) {
        	// log.debug("AgendaServiceImpl userBeanList check : {}", userBean.toString());
        	for (AgendaBean agendaBean : userBean.getAgendaBeans()) {
//        		log.debug("TESTTESTSTESTEST : {}",agendaBean.toString());
				temps.add(agendaBean);
			}
        	for (AgendaBean agendaBean : temps) {
        		// log.debug("TESTTESTSTESTEST : {}",agendaBean.toString());
			}
        }
        
//        log.debug("tempssssss size : {}", temps.size());
        for (AgendaBean agendaBean : temps) {
        	// log.debug("AgendaServiceImpl AgendaBean temp check : ", agendaBean.getAgendaId());
		}
        
        
        for (AgendaBean temp : temps) {
//        	log.debug("AgendaServiceImpl AgendaBean temp check : ", temp.toString());
//        	log.debug("AgendaServiceImpl temps.indexOf temp : {}", temps.indexOf(temp));
//        	log.debug("AgendaServiceImpl temps.lastIndexOf temp : {}", temps.lastIndexOf(temp));
            if (temps.indexOf(temp) != temps.lastIndexOf(temp)) {
                agendaBeanSet.add(temp);
                log.debug("AgendaServiceImpl.showAgendaBeansWithUsers AgendaBean Check : {}", temp.toString());
            }
        }
        List<AgendaBean> result = new ArrayList<>(agendaBeanSet);

        return result;
        
    }
    
}
