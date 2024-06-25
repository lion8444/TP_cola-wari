package jp.co.sss.management.service.agenda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.repository.AgendaEntryRepository;
import jp.co.sss.management.repository.AgendaRepository;
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
    AgendaRepository agendaRepository;

    @Autowired
    AgendaEntryRepository agendaEntryRepository;

    @Autowired
    AgendaBeanTools agendaBeanTools;

    @Autowired
    UserBeanTools userBeanTools;

    @Override
    public List<AgendaBean> showAgendaBeansWithUsers(List<UserBean> userBeans) {

        
        List<AgendaBean> results = new ArrayList<>();

        if (userBeans.size() == 1) {
            results = userBeans.get(0).getAgendaBeans();
            return results;
        }

        List<Integer> agendaIdList = userBeans.get(0).getAgendaBeans().stream().map(AgendaBean::getAgendaId).collect(Collectors.toList());

        for (UserBean userBean : userBeans) {
            Set<Integer> agendaIdSet = new HashSet<>(userBean.getAgendaBeans().stream().map(AgendaBean::getAgendaId).collect(Collectors.toList()));
            agendaIdList.retainAll(agendaIdSet);
        }
        results.clear();
        for (Integer agendaId : agendaIdList) {
            log.debug("AgendaServiceImpl agendaId check : {}", agendaId);
            results.add(agendaBeanTools.copyEntityToBean(agendaRepository.getReferenceById(agendaId)));
        }

        return results;

    }

}
