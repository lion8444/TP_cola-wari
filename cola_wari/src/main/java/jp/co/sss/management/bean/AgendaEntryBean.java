package jp.co.sss.management.bean;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Agenda user Entity class(案件・担当者(企業担当者))
 */
@Getter
@Setter
@NoArgsConstructor
public class AgendaEntryBean {

	/**
	 * 案件・担当者ID
	 */
	private Integer entryId;
	
	/**
	 * 案件情報
	 */
	private AgendaBean agendaBean;

	/**
	 * 案件の担当者情報(企業担当社)
	 */
	private List<UserBean> userBeans;

	/**
	 * 案件の担当者情報(企業担当社)
	 */
	private List<AgentBean> agentBeans;

}
