package jp.co.sss.management.form;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Agent Form class
 * 顧客企業の担当者
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AgentForm implements Serializable {

	/**
	 * 担当者ID
	 */
	private Integer agentId;
	
	/**
	 * 担当者名
	 */
	@NotBlank
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String agentName;

	/**
	 * 担当者が所属している部署名
	 */
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String department;

	/**
	 * 担当者が所属しているチーム
	 */
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String team;

	/**
	 * 担当者の役職
	 */
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String position;

	/**
	 * 担当者の電話番号
	 */
	@NotBlank
	@Pattern(regexp = "^[0-9]+$", message = "{userRegist.numberpattern.message}")
	private String tel;

	/**
	 * 担当者のメールアドレス
	 */
	@NotBlank
	@Email
	private String email;

	private Integer comId;

	private List<AgentForm> agentForms;

}
