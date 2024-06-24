package jp.co.sss.management.service.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.management.bean.RestScheduleBean;
import jp.co.sss.management.bean.ScheduleBean;
import jp.co.sss.management.bean.ScheduleEntryBean;
import jp.co.sss.management.bean.UserBean;
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

    @Autowired
    HttpSession session;

    @Override
    public List<RestScheduleBean> showRestScheduleBeans() {
        List<RestScheduleBean> restScheduleBeans = new ArrayList<>();
        List<Schedule> schedules = scheduleRepository.findAll();

        LocalDateTime nowdDateTime = LocalDateTime.now();

        for (Schedule schedule : schedules) {
            log.debug("ScheduleServiceImpl Schedule EndDate check : {}", schedule.getEndDate());
            if (schedule.getEndDate() == null) {
                continue;
            }
            if (!schedule.getEndDate().isAfter(nowdDateTime)) {
                schedule.setStatus(1);
                scheduleRepository.save(schedule);
            }
        }

        List<ScheduleBean> scheduleBeans = scheduleBeanTools.copyEntityListToBeanList(schedules);

        for (ScheduleBean scheduleBean : scheduleBeans) {
            RestScheduleBean restScheduleBean = new RestScheduleBean();

            restScheduleBean.setScheduleId(scheduleBean.getScheduleId());
            restScheduleBean.setTitle(scheduleBean.getTitle());
            restScheduleBean.setDescription(scheduleBean.getDescription());
            restScheduleBean.setStart(scheduleBean.getStartDate());
            restScheduleBean.setEnd(scheduleBean.getEndDate());

            restScheduleBean.setAllDay(isWithinWorkingHours(scheduleBean.getStartDate(), scheduleBean.getEndDate()));

            Schedule tempSchedule = new Schedule();
            tempSchedule.setScheduleId(scheduleBean.getScheduleId());
            List<User> users = scheduleEntryRepository.findByScheduleUsers(tempSchedule);

            UserBean userBean = (UserBean) session.getAttribute("user");

            if (scheduleBean.getStatus() == 0) {
                restScheduleBean.setClassName("fc-bg-blue");
            }

            for (User user : users) {
                if (user.getUserId().equals(userBean.getUserId())) {
                    restScheduleBean.setClassName("fc-bg-green");
                    break;
                }
            }

            if (scheduleBean.getStatus() == 1) {
                restScheduleBean.setClassName("fc-bg-gray");
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
    @Transactional
    public int insertSchedule(ScheduleForm form) {
        log.debug("ScheduleServiceImpl.insertSchedule ScheduleForm check : {}", form.getStartDate());
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        log.debug("Schedule DescriptionTest : {}", form.getDescription());
        Schedule schedule = scheduleBeanTools.copyFormToEntity(form);
        log.debug("Schedule DescriptionTest : {}", schedule.getDescription());
        try {
            schedule.setStartDate(parseDateString(form.getStartDate()));
            schedule.setEndDate(parseDateString(form.getEndDate()));
        } catch (DateTimeParseException e) {
            log.error("Failed to parse date/time: {}" + e.getMessage());
            // Handle parsing exception as needed
        }
        log.debug("ScheduleServiceImpl.insertSchedule Schedule check : {}", schedule.getStartDate());

        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm
        // a");
        // Date tempDate = null;
        // try {
        // tempDate = simpleDateFormat.parse(form.getStartDate());
        // log.debug("ScheduleServiceImpl date check : {}", tempDate.toString());
        // schedule.setStartDate(new java.sql.Date(tempDate.getTime()));
        //
        // tempDate = simpleDateFormat.parse(form.getEndDate());
        // log.debug("ScheduleServiceImpl date check : {}", tempDate.toString());
        // schedule.setEndDate(new java.sql.Date(tempDate.getTime()));
        // } catch (ParseException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        // log.debug("ScheduleServiceImpl.insertSchedule dateformat check :
        // {}",simpleDateFormat.parse(form.getStartDate()));
        // schedule.setStartDate();

        Agenda agenda = new Agenda();
        if (form.getAgendaId() != null) {
            agenda = agendaRepository.getReferenceById(form.getAgendaId());
        }
        agendaRepository.save(agenda);

        schedule.setAgenda(agenda);
        if (scheduleRepository.save(schedule) == null) {
            return 0;
        }

        if (!(scheduleEntryRepository.findByScheduleUsers(schedule).isEmpty())) {
            scheduleEntryRepository.deleteBySchedule(schedule);
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

    @Override
    @Transactional
    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = scheduleRepository.getReferenceById(scheduleId);

        scheduleEntryRepository.deleteAllBySchedule(schedule);
        scheduleRepository.delete(schedule);

        return;
    }

    private LocalDateTime parseDateString(String dateString) {
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(5, 7));
        int day = Integer.parseInt(dateString.substring(8, 10));
        int hour = Integer.parseInt(dateString.substring(11, 13));
        int minute = Integer.parseInt(dateString.substring(14, 16));

        String period = dateString.substring(17, 19); // "am" 또는 "pm"
        if (period.equalsIgnoreCase("pm") || period.equalsIgnoreCase("午後")) {
            hour += 12; // 오후일 경우 시간에 12를 더해줌
        }

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private static boolean isWithinWorkingHours(String startDateStr, String endDateStr) {

        if (startDateStr == null || endDateStr == null) {
            return false;
        }

        LocalDateTime startDate = LocalDateTime.parse(startDateStr);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        // 오전 9시와 오후 6시의 기준 시간
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        if (endDate.toLocalTime().equals(java.time.LocalTime.MIDNIGHT)) {
            endDate = endDate.minusMinutes(1);
        }

        // startDate가 오전 9시 이후인지 확인
        boolean isStartDateAfterOrEqualTo9AM = !startDate.toLocalTime().isAfter(startTime);

        // endDate가 오후 6시 이전인지 확인
        boolean isEndDateBeforeOrEqualTo6PM = !endDate.toLocalTime().isBefore(endTime);

        // startDate가 오전 9시 이전이거나 같고, endDate가 오후 6시 이후이거나 같으면 true 반환
        return isStartDateAfterOrEqualTo9AM && isEndDateBeforeOrEqualTo6PM;
    }

    @Override
    public List<RestScheduleBean> showRestScheduleBeansByWeek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showRestScheduleBeansByWeek'");
    }

}
