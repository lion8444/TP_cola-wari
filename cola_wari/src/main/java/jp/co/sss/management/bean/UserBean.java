package jp.co.sss.management.bean;

import lombok.NoArgsConstructor;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User Bean class
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserBean {
	
	/**
	 * ユーザーID
	 */
	private int userId;
	
	/**
	 * ユーザー名
	 */
	private String userName;

	/**
	 * パスワード
	 */
	private String password;

	/**
	 * ユーザーが所属しているチーム
	 */
	private String team;

	/**
	 * ユーザーの役職
	 */
	private String position;

	/**
	 * ユーザーの電話番号
	 */
	private String tel;

	/**
	 * ユーザーのメールアドレス
	 */
	private String email;

	/**
	 * ユーザーアカウントの状態
	 * 0：使用可能、1：使用中止(削除)
	 */
	private int status;

	/**
	 * ユーザーの権限
	 * 0：一般社員、1：管理者
	 */
	private int auth;

	/**
	 * 営業部員が担当する案件リスト
	 */
	private List<AgendaBean> agendaBeans;

}
