package jp.co.sss.management.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Agenda Entity class
 * 案件エンティティクラス
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AgendaBean {

	/**
	 * 案件ID
	 */
	private Integer agendaId;

	/**
	 * 案件タイトル
	 */
	private String title;

	/**
	 * 案件の説明
	 */
	private String description;

	/**
	 * 案件の状態
	 * 0：進行中、1：完了
	 */
	private int status;

	/**
	 * 報告
	 */
	private String report;

	/**
	 * 契約金
	 */
	private int sales;

	/**
	 * 案件開始日程(YYYY/MM/DD HH:mm)
	 */
	private String startDate;

	/**
	 * 案件終了日程(YYYY/MM/DD HH:mm)
	 */
	private String endDate;

	/**
	 * 案件を与えた企業名
	 */
	private String comName;

}
