package jp.co.sss.management.bean;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Company Entity class
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CompanyBean {

	/**
	 * 企業ID
	 */
	private Integer comId;

	/**
	 * 企業名
	 */
	private String comName;

	/**
	 * 企業注所
	 */
	private String comAddr;

	/**
	 * 企業サイトURL
	 */
	private String comUrl;

	/**
	 * 企業説明
	 */
	private String description;

	/**
	 * 企業情報の登録日程(YYYY/MM/DD HH:mm)
	 */
	private String insertDate;

	/**
	 * 企業情報の修正日程(YYYY/MM/DD HH:mm)
	 */
	private String updateDate;

	/**
	 * 企業の業界情報(カテゴリ)
	 */
	private String cateName;

	/**
	 * 企業の担当者リスト
	 */
	private List<AgentBean> agentBeans;

}
