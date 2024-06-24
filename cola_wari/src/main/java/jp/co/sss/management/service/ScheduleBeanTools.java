package jp.co.sss.management.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.ScheduleBean;
import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.Schedule;
import jp.co.sss.management.form.ScheduleForm;
import jp.co.sss.management.repository.AgendaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleBeanTools {

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    AgendaBeanTools agendaBeanTools;

    public ScheduleBean copyEntityToBean(Schedule entity) {
        ScheduleBean bean = new ScheduleBean();
        BeanUtils.copyProperties(entity, bean);
        
        bean.setAgendaBean(agendaBeanTools.copyEntityToBean(agendaRepository.getReferenceById(entity.getAgenda().getAgendaId())));

		if (entity.getStartDate() == null) {
			bean.setStartDate(null);
		} else if(entity.getEndDate() == null) {
			bean.setEndDate(null);
		} else {
			if (entity.getEndDate().toLocalTime().equals(java.time.LocalTime.MIDNIGHT)) {
				entity.setEndDate(entity.getEndDate().minusMinutes(1));
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");
			bean.setStartDate(entity.getStartDate().format(formatter));
			bean.setEndDate(entity.getEndDate().format(formatter));
		}

        return bean;
    }

    public List<ScheduleBean> copyEntityListToBeanList(List<Schedule> entityList) {
        List<ScheduleBean> beanList = new ArrayList<ScheduleBean>();

		for (Schedule entity : entityList) {
			ScheduleBean bean = new ScheduleBean();
			BeanUtils.copyProperties(entity, bean);

			if (entity.getStartDate() == null) {
				bean.setStartDate(null);
			} else if(entity.getEndDate() == null) {
				bean.setEndDate(null);
			} else {
				if (entity.getEndDate().toLocalTime().equals(java.time.LocalTime.MIDNIGHT)) {
					entity.setEndDate(entity.getEndDate().minusMinutes(1));
				}
				bean.setStartDate(entity.getStartDate().toString());
				bean.setEndDate(entity.getEndDate().toString());
			}
			
			
            bean.setAgendaBean(agendaBeanTools.copyEntityToBean(agendaRepository.getReferenceById(entity.getAgenda().getAgendaId())));

			beanList.add(bean);
		}

		return beanList;
    }

    public Schedule copyFormToEntity(ScheduleForm form) {
        Agenda agenda = new Agenda();
		Schedule entity = new Schedule();

		BeanUtils.copyProperties(form, entity);

		if (form.getScheduleId() != null) {
			entity.setScheduleId(form.getScheduleId());
		}

		agenda.setAgendaId(form.getAgendaId());
		entity.setAgenda(agenda);

		return entity;
	}
}
