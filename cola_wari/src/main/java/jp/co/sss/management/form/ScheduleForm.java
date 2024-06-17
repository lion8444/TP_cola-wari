package jp.co.sss.management.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Meeting Entity class
 * 案件に関するミーティング
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleForm implements Serializable {

	/**
	 * ミーティングID
	 */
	private Integer scheduleId;
	
	/**
	 * ミーティング名
	 */
	@NotBlank
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String title;

	/**
	 * ミーティング説明
	 */
	@Size(min = 1, max = 2000, message = "{text.maxsize.message}")
	private String description = "";

	/**
	 * ミーティング状態
	 * 0：予定、1：完了
	 */
	private int status;

	/**
	 * ミーティング場所
	 */
	@Size(min = 1, max = 100, message = "{text.maxsize.message}")
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
	 * ミーティングの案件ID
	 */
	private Integer agendaId;

}
