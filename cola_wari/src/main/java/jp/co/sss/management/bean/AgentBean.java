package jp.co.sss.management.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Agent Entity class
 * 顧客企業の担当者エンティティクラス
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AgentBean {

	/**
	 * 担当者ID
	 */
	private Integer agentId;
	
	/**
	 * 担当者名
	 */
	private String agentName;

	/**
	 * 担当者が所属している部署名
	 */
	private String department;

	/**
	 * 担当者が所属しているチーム
	 */
	private String team;

	/**
	 * 担当者の役職
	 */
	private String position;

	/**
	 * 担当者の電話番号
	 */
	private String tel;

	/**
	 * 担当者のメールアドレス
	 */
	private String email;

}
