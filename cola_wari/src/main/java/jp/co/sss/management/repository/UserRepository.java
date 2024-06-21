package jp.co.sss.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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

    User findByEmail(String email);

    List<User> findByStatus(int i);

}