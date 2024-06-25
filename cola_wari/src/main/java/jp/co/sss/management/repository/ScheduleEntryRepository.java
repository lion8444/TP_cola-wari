package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

	@Query("select s.user from ScheduleEntry s where s.schedule = :schedule")
    List<User> findByScheduleUsers(Schedule schedule);
	
	/**
	 * ユーザIDのみ入ったuserを条件に検索
	 * @param user ユーザ
	 */
	@Query("select s.schedule from ScheduleEntry s where s.user = :user")
	List<Schedule> findByUserSchedules(User user);


    void deleteAllBySchedule(Schedule schedule);

    void deleteBySchedule(Schedule schedule);


}