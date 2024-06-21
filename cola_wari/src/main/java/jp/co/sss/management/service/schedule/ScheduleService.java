package jp.co.sss.management.service.schedule;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.sss.management.bean.RestScheduleBean;
import jp.co.sss.management.bean.ScheduleEntryBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.form.ScheduleForm;

@Service
public interface ScheduleService {
    
    List<RestScheduleBean> showRestScheduleBeans();

    ScheduleEntryBean showScheduleDetail(int scheduleId);

    List<RestScheduleBean> showRestScheduleBeansByWeek();

    int insertSchedule(ScheduleForm form);
}
