package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Schedule;
import jp.co.sss.management.entity.ScheduleEntry;
import jp.co.sss.management.entity.User;


/**
 * スケジュールテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface ScheduleEntryRepository extends JpaRepository<ScheduleEntry, Integer> {

    List<User> findBySchedule_ScheduleId(Integer scheduleId);
}