package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Agent;
import jp.co.sss.management.entity.Company;

/**
 * 企業担当者テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {

    List<Agent> findByCompanyAndDeleteFlag(Company company, int deleteFlag);



    void deleteAllByCompany(Company companyEntity);
    
    Agent findByAgentIdAndCompanyAndDeleteFlag(Integer agentId, Company company, int deleteFlag);


}