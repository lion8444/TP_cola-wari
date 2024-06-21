package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Agent;

/**
 * 企業担当者テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
	List<Agent> findByCompany_ComId(int comId);


}