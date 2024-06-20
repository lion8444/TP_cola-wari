package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

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
	/**
	 * ユーザIDのみ入ったuserを条件に検索
	 * @param user ユーザ
	 */
	@Query("select a.schedule from ScheduleEntry a where a.user = :user")
	List<Schedule> findByUser(User user);

}