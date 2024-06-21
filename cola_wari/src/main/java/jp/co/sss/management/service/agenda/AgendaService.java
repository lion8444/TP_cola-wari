package jp.co.sss.management.service.agenda;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.AgendaBean;
import jp.co.sss.management.bean.UserBean;

@Service
public interface AgendaService {
    
    List<AgendaBean> showAgendaBeansWithUsers(List<UserBean> userBeans);

}
