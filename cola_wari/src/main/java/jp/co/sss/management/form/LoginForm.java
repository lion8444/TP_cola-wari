package jp.co.sss.management.form;

import lombok.NoArgsConstructor;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jp.co.sss.management.annotation.EmailCheck;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Login Form class
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EmailCheck
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
