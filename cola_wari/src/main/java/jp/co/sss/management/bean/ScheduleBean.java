package jp.co.sss.management.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Meeting Entity class
 * 案件に関するミーティング
 */
@Getter
@Setter
@NoArgsConstructor
public class ScheduleBean {

	/**
	 * ミーティングID
	 */
	private Integer scheduleId;
	
	/**
	 * ミーティング名
	 */
	private String title;

	/**
	 * ミーティング説明
	 */
	private String description;

	/**
	 * ミーティング状態
	 * 0：予定、1：完了
	 */
	private int status;

	/**
	 * ミーティング場所
	 */
	private String addr;

	/**
	 * スケジュール開始日付(YYYY/MM/DD HH:mm)
	 */
	private String startDate;

	/**
	 * スケジュール終了日付(YYYY/MM/DD HH:mm)
	 */
	private String endDate;

	/**
	 * ミーティングの案件
	 */
	private AgendaBean agendaBean;

}
