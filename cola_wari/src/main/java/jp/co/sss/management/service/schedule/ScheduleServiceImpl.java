package jp.co.sss.management.service.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.RestScheduleBean;
import jp.co.sss.management.bean.ScheduleBean;
import jp.co.sss.management.bean.ScheduleEntryBean;
import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.Schedule;
import jp.co.sss.management.entity.ScheduleEntry;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.ScheduleForm;
import jp.co.sss.management.repository.AgendaRepository;
import jp.co.sss.management.repository.ScheduleEntryRepository;
import jp.co.sss.management.repository.ScheduleRepository;
import jp.co.sss.management.service.AgendaBeanTools;
import jp.co.sss.management.service.ScheduleBeanTools;
import jp.co.sss.management.service.UserBeanTools;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleEntryRepository scheduleEntryRepository;

    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    ScheduleBeanTools scheduleBeanTools;

    @Autowired
    AgendaBeanTools agendaBeanTools;

    @Autowired
    UserBeanTools userBeanTools;

    @Override
    public List<RestScheduleBean> showRestScheduleBeans() {
        List<RestScheduleBean> restScheduleBeans = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll();

        List<ScheduleBean> scheduleBeans = scheduleBeanTools.copyEntityListToBeanList(schedules);

        for (ScheduleBean scheduleBean : scheduleBeans) {
            RestScheduleBean restScheduleBean = new RestScheduleBean();

            restScheduleBean.setScheduleId(scheduleBean.getScheduleId());
            restScheduleBean.setTitle(scheduleBean.getTitle());
            restScheduleBean.setDescription(scheduleBean.getDescription());
            restScheduleBean.setStart(scheduleBean.getStartDate());
            restScheduleBean.setEnd(scheduleBean.getEndDate());

            if (scheduleBean.getStatus() == 0) {
                restScheduleBean.setClassName("fc-bg-bule");
            } else {
                restScheduleBean.setClassName("fc-bg-red");
            }

            restScheduleBeans.add(restScheduleBean);
        }

        return restScheduleBeans;
        
    }

    @Override
    public ScheduleEntryBean showScheduleDetail(int scheduleId) {
        ScheduleEntryBean scheduleEntryBean = new ScheduleEntryBean();

        Schedule schedule = scheduleRepository.getReferenceById(scheduleId);

        List<User> users = scheduleEntryRepository.findByScheduleUsers(schedule);
       
        scheduleEntryBean.setScheduleBean(scheduleBeanTools.copyEntityToBean(schedule));
        scheduleEntryBean.setUserBeans(userBeanTools.copyEntityListToBeanList(users));
        
        return scheduleEntryBean;
    }



    @Override
    public List<RestScheduleBean> showRestScheduleBeansByWeek() {
        
        throw new UnsupportedOperationException("Unimplemented method 'showRestScheduleBeansByWeek'");
    }

    @Override
    public int insertSchedule(ScheduleForm form) {
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        Schedule schedule = scheduleBeanTools.copyFormToEntity(form);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy hh:mm a", Locale.ENGLISH);
        Date tempDate = null;
        try {
            tempDate = simpleDateFormat.parse(form.getStartDate());
            log.debug("ScheduleServiceImpl date check : {}", tempDate.toString());
            schedule.setStartDate(new java.sql.Date(tempDate.getTime()));

            tempDate = simpleDateFormat.parse(form.getEndDate());
            log.debug("ScheduleServiceImpl date check : {}", tempDate.toString());
            schedule.setEndDate(new java.sql.Date(tempDate.getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // log.debug("ScheduleServiceImpl.insertSchedule dateformat check : {}",simpleDateFormat.parse(form.getStartDate()));
        // schedule.setStartDate();
        
        Agenda agenda = new Agenda();
        if(form.getAgendaId() != null) {
        	agenda = agendaRepository.save(agendaRepository.getReferenceById(form.getAgendaId()));
        }
        

        schedule.setAgenda(agenda);
        if (scheduleRepository.save(schedule) == null) {
            return 0;
        }

        scheduleEntry.setSchedule(schedule);

        for (Integer userId : form.getUserIdList()) {
            User user = new User();
            user.setUserId(userId);

            scheduleEntry.setUser(user);
            if (scheduleEntryRepository.save(scheduleEntry) == null) {
                return 0;
            }
        }

        return 1;
    }

    
}
