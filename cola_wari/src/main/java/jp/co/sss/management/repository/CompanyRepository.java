package jp.co.sss.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.Company;

/**
 * 企業テーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}