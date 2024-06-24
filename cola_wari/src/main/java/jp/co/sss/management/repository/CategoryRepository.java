package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.ComCategory;

@Repository
public interface CategoryRepository extends JpaRepository<ComCategory, Integer> {

	/**
	 * statusが0のデータを取得し、主キー昇順で返す
	 * @return statusが0のUserエンティティのリスト
	 */
	List<ComCategory> findAllByOrderByCateIdAsc();

}
