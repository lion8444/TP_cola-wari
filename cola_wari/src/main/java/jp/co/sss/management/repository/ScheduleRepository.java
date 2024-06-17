package jp.co.sss.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Schedule;

/**
 * スケジュールテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}