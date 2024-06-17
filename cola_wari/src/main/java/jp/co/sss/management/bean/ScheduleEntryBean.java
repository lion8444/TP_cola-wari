package jp.co.sss.management.bean;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Meeting Entry Entity class(ミーティング・担当者)
 */
@Getter
@Setter
@NoArgsConstructor
public class ScheduleEntryBean {

	/**
	 * ミーティング参加する営業社員ID
	 */
	private Integer entryId;
	
	/**
	 * 案件情報
	 */
	private ScheduleBean scheduleBean;

	/**
	 * 案件の担当者情報
	 */
	private List<UserBean> userBeans;

}
