package jp.co.sss.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Agenda;
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.User;

import java.util.List;


/**
 * 案件テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface AgendaEntryRepository extends JpaRepository<AgendaEntry, Integer> {

	@Query("select a.agenda from AgendaEntry a where a.user = :user")
	List<Agenda> findByUserAgendas(User user);

}