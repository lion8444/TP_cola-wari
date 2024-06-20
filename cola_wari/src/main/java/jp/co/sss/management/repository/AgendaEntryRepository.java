package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.User;

/**
 * 案件中間テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface AgendaEntryRepository extends JpaRepository<AgendaEntry, Integer> {

	/**
	 * ユーザIDを条件に検索
	 * @param userId ユーザID
	 */
//	List<AgendaEntry> findByUser_UserId(Integer userId);
	//抜き出したい情報、情報元のテーブル、探す数値
	@Query("select a.agenda from AgendaEntry a where a.user = :user")
	List<Agenda> findByUser(User user);
	
	
//	List<Agenda> findByUserId(int userId);


}