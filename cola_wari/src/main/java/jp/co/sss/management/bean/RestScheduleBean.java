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
public class RestScheduleBean {

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
	 * スケジュール開始日付(YYYY/MM/DD HH:mm)
	 */
	private String start;

	/**
	 * スケジュール終了日付(YYYY/MM/DD HH:mm)
	 */
	private String end;

	private String className;
	
	private boolean allDay;


}
