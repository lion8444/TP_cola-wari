package jp.co.sss.management.form;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jp.co.sss.management.annotation.LoginCheck;
import lombok.Data;

/**
 * Login Form class
 */
@Data
@LoginCheck
public class LoginForm implements Serializable {

	/**
	 * ユーザメールアドレス
	 */
	@NotBlank
	@Email
	private String email;

	/**
	 * パスワード
	 */
	@NotBlank
	private String password;
}
