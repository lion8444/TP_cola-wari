package jp.co.sss.management.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordForm {

	/**
	 * 昔のパスワード
	 */
	@NotBlank
	private String oldPassword;

	/**
	 * 新しいのパスワード
	 */
	@NotBlank
	private String newPassword;

	/**
	 * 新しいのパスワードの確認
	 */
	@NotBlank
	private String checkPassword;

}
