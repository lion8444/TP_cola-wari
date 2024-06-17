package jp.co.sss.management.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Agenda Entity class
 * 案件エンティティクラス
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agenda")
public class Agenda {

	/**
	 * 案件ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agenda_gen")
	@SequenceGenerator(name = "seq_agenda_gen", sequenceName = "seq_agenda", allocationSize = 1)
	private Integer agendaId;
	
	/**
	 * 案件タイトル
	 */
	@Column
	private String title;

	/**
	 * 案件の説明
	 */
	@Column
	private String description;

	/**
	 * 案件の状態
	 * 0：進行中、1：完了
	 */
	@Column
	private int status;

	/**
	 * 報告
	 */
	@Column
	private String report;

	/**
	 * 契約金
	 */
	@Column
	private int sales;

	/**
	 * 案件開始日程(YYYY/MM/DD HH:mm)
	 */
	@Column
	private String startDate;

	/**
	 * 案件終了日程(YYYY/MM/DD HH:mm)
	 */
	@Column
	private String endDate;


	/**
	 * 案件を担当している営業社員
	 */
	@OneToMany(mappedBy = "agenda")
	private List<AgendaEntry> agendaEntries;

	/**
	 * 案件を担当している営業社員
	 */
	// @OneToMany(mappedBy = "agendaAgent")
	// private List<AgendaAgent> agendaAgents;

	/**
	 * 案件に関するミーティングリスト
	 */
	@OneToMany(mappedBy = "agenda")
	private List<Schedule> schedules;

}
