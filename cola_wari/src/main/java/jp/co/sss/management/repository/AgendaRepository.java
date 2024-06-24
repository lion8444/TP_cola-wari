package jp.co.sss.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Agenda;

/**
 * 案件テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

	/**
	 * 案件IDと削除フラグを条件に検索
	 * @param agendaId 案件ID
	 * @param status 削除フラグ
	 * @return 案件エンティティ
	 */
	Agenda findByAgendaIdAndStatus(Integer agendaId, int status);
	
	@Query("SELECT a FROM Agenda a WHERE a.agendaId = (SELECT MAX(a2.agendaId) FROM Agenda a2)") 
	Agenda findByMaxAgendaId();

}