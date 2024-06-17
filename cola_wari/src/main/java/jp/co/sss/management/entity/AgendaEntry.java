package jp.co.sss.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Agenda user Entity class(案件・担当者(営業社員))
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "agneda_entry")
public class AgendaEntry {

	/**
	 * 案件・担当者ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agenda_entry_gen")
	@SequenceGenerator(name = "seq_agenda_entry_gen", sequenceName = "seq_agenda_entry", allocationSize = 1)
	private Integer entryId;
	
	/**
	 * 案件情報
	 */
	@ManyToOne
	@JoinColumn(name = "agenda_id", referencedColumnName = "agendaId")
	private Agenda agenda;

	/**
	 * 案件の担当者情報(営業社員)
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;

	/**
	 * 案件の担当者情報(企業担当社)
	 */
	@ManyToOne
	@JoinColumn(name = "agent_id", referencedColumnName = "agentId")
	private Agent agent;

}
