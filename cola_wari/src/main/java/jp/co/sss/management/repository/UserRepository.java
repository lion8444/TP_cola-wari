package jp.co.sss.management.repository;

import java.util.List;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.management.entity.User;

/**
 * usersテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * メールアドレスと削除フラグに該当する会員情報を検索 Validatorで利用
	 * @param email メールアドレス
	 * @param status 削除フラグ 0：使用可能、1：使用不可
	 * @return 会員エンティティ
	 */
	User findByEmailAndStatus(String email, int status);

	/**
	 * 会員IDと削除フラグを条件に検索
	 * @param userId 会員ID
	 * @param status 削除フラグ
	 * @return 会員エンティティ
	 */
	User findByUserIdAndStatus(Integer userId, int status);

	/**
	 * 削除フラグを条件に検索
	 * @param status
	 * @return 会員エンティティ
	 */
	List<User> findByStatus(int status);

	/**
	 * 
	 * @param email
	 * @return 会員エンティティ
	 */
	User findByEmail(String email);

	/**
	 * ユーザIDを条件に検索
	 * @param userId
	 * @return 会員エンティティ
	 */
	User findByUserId(Integer userId);

	/**
	 * statusが0のデータを取得し、主キー昇順で返す
	 * @return statusが0のUserエンティティのリスト
	 */
	List<User> findByStatusOrderByUserIdAsc(int status);

	/**
	 * 
	 * 
	 */
	@Query("SELECT u FROM User u WHERE u.status = :status and u.userName LIKE %:keyword% ORDER BY FUNCTION('NLSSORT', u.position, 'NLS_SORT=BINARY_AI') ASC")
	List<User> findByKeywordAndStatusOrderByPositionASC(@Param(value = "keyword") String keyword,
			@Param(value = "status") int status);

	@Query("SELECT u FROM User u WHERE u.status = :status and u.userName LIKE %:keyword% ORDER BY FUNCTION('NLSSORT', u.userName, 'NLS_SORT=BINARY_AI') ASC")
	List<User> findByKeywordAndStatusOrderByUserNameSC(@Param(value = "keyword") String keyword,
			@Param(value = "status") int status);

	@Query("SELECT u FROM User u WHERE u.status = :status and u.userName LIKE %:keyword% ORDER BY FUNCTION('NLSSORT', u.team, 'NLS_SORT=BINARY_AI') ASC")
	List<User> findByKeywordAndStatusOrderByTeamSC(@Param(value = "keyword") String keyword,
			@Param(value = "status") int status);

	List<User> findByStatusOrderByUserIdAsc(int i);
}
