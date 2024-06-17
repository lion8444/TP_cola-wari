package jp.co.sss.management.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * User Entity class
 * 営業部員エンティティクラス
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	/**
	 * ユーザーID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users_gen")
	@SequenceGenerator(name = "seq_users_gen", sequenceName = "seq_users", allocationSize = 1)
	private Integer userId;

	/**
	 * ユーザーのメールアドレス
	 */
	@Column
	private String email;

	/**
	 * パスワード
	 */
	@Column
	private String password;

	/**
	 * ユーザー名
	 */
	@Column
	private String userName;

	/**
	 * ユーザーが所属しているチーム
	 */
	@Column
	private String team;

	/**
	 * ユーザーの役職
	 */
	@Column
	private String position;

	/**
	 * ユーザーの電話番号
	 */
	@Column
	private String tel;

	/**
	 * ユーザーアカウントの状態
	 * 0：使用可能、1：使用不可(登録前・削除)
	 */
	@Column
	private int status;

	/**
	 * ユーザーの権限
	 * 0：一般社員、1：管理者
	 */
	@Column
	private int auth;

	/**
	 * ユーザーが参加している案件リスト
	 */
	@OneToMany(mappedBy = "user")
	private List<AgendaEntry> agendaEntries;

	/**
	 * ユーザーが参加しているミーティングリスト
	 */
	@OneToMany(mappedBy = "user")
	private List<ScheduleEntry> scheduleEntries;

}
