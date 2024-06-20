package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Schedule;
import jp.co.sss.management.entity.ScheduleEntry;


/**
 * スケジュールテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}