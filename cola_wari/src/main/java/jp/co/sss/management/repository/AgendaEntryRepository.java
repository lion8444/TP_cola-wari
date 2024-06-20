package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.User;

/**
 * usersテーブル用リポジトリ
 */

/**
 * 案件テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface AgendaEntryRepository extends JpaRepository<AgendaEntry, Integer> {

	List<AgendaEntry> findByAgendaStatus(int status);

	@Query("SELECT ae FROM AgendaEntry ae WHERE ae.user.userId = :userId and ae.agenda.status = :status")
	List<AgendaEntry> findByUserId(@Param("userId") Integer userId, @Param("status") int status);

	//@Query("SELECT ae " +
	//	"FROM AgendaEntry ae " +
	//	"WHERE user.userId =: userId")
	//List<AgendaEntry> findByAgendaStatus(@Param("userId") Integer userId);
	
	@Query("select a.agenda from AgendaEntry a where a.user = :user")
	List<Agenda> findByUserAgendas(User user);

}