package jp.co.sss.management.form;

import lombok.NoArgsConstructor;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jp.co.sss.management.annotation.EmailCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User Form class
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EmailCheck
public class UserForm implements Serializable {
	
	/**
	 * ユーザーID
	 */
	private String userId;
	
	/**
	 * ユーザー名
	 */
	@NotBlank
	@Email
	private String email;

	/**
	 * パスワード
	 */
	@NotBlank
	private String password;

	/**
	 * ユーザーが所属しているチーム
	 */
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String team;

	/**
	 * ユーザーの役職
	 */
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String position;

	/**
	 * ユーザーの電話番号
	 */
	@NotBlank
	@Pattern(regexp = "^[0-9]+$", message = "{userRegist.numberpattern.message}")
	private String tel;

	/**
	 * ユーザーのメールアドレス
	 */
	@NotBlank
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String userName;

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

}
