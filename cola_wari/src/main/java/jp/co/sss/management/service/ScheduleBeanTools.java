package jp.co.sss.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.ScheduleBean;
import jp.co.sss.management.entity.Schedule;
import jp.co.sss.management.repository.AgendaRepository;

@Service
public class ScheduleBeanTools {

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    AgendaBeanTools agendaBeanTools;

    public ScheduleBean copyEntityToBean(Schedule entity) {
        ScheduleBean scheduleBean = new ScheduleBean();
        BeanUtils.copyProperties(entity, scheduleBean);
        
        scheduleBean.setAgendaBean(agendaBeanTools.copyEntityToBean(agendaRepository.getReferenceById(entity.getAgenda().getAgendaId())));

        return scheduleBean;
    }

    public List<ScheduleBean> copyEntityListToBeanList(List<Schedule> entityList) {
        List<ScheduleBean> beanList = new ArrayList<ScheduleBean>();

		for (Schedule entity : entityList) {
			ScheduleBean bean = new ScheduleBean();
			BeanUtils.copyProperties(entity, bean);

            bean.setAgendaBean(agendaBeanTools.copyEntityToBean(agendaRepository.getReferenceById(entity.getAgenda().getAgendaId())));

			beanList.add(bean);
		}

		return beanList;
    }
}
