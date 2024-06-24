package jp.co.sss.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.ComCategory;

/**
 * 企業カテゴリテーブル用リポジトリ
 *
 * 
 */
@Repository
public interface ComCategoryRepository extends JpaRepository<ComCategory, Integer>{

}
