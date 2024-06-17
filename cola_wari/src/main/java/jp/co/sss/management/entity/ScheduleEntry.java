package jp.co.sss.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Meeting Entry Entity class(ミーティング・担当者)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule_entry")
public class ScheduleEntry {

	/**
	 * ミーティング参加する営業社員ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_schedule_entry_gen")
	@SequenceGenerator(name = "seq_schedule_entry_gen", sequenceName = "seq_schedule_entry", allocationSize = 1)
	private Integer entryId;
	
	/**
	 * 案件情報
	 */
	@ManyToOne
	@JoinColumn(name = "schedule_id", referencedColumnName = "scheduleId")
	private Schedule schedule;

	/**
	 * 案件の担当者情報
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;

}
