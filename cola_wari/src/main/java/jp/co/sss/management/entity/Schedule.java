package jp.co.sss.management.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {

	/**
	 * ミーティングID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedule_gen")
	@SequenceGenerator(name = "seq_schedule_gen", sequenceName = "seq_schedule", allocationSize = 1)
	private Integer scheduleId;
	
	/**
	 * ミーティング名
	 */
	@Column
	private String title;

	/**
	 * ミーティング説明
	 */
	@Column
	private String description;

	/**
	 * ミーティング状態
	 * 0：予定、1：完了
	 */
	@Column
	private int status;

	/**
	 * ミーティング場所
	 */
	@Column
	private String addr;

	/**
	 * スケジュール開始日付(YYYY/MM/DD HH:mm)
	 */
	@Column
	private Date startDate;

	/**
	 * スケジュール終了日付(YYYY/MM/DD HH:mm)
	 */
	@Column
	private Date endDate;

	/**
	 * ミーティングの案件
	 */
	@ManyToOne
	@JoinColumn(name = "agenda_id", referencedColumnName = "agendaId")
	private Agenda agenda;

	/**
	 * ミーティング参加する営業社員(サブテーブル)
	 */
	@OneToMany(mappedBy = "schedule")
	private List<ScheduleEntry> scheduleEntries;

}
