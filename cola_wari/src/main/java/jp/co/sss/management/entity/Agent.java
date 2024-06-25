package jp.co.sss.management.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
 * Agent Entity class
 * 顧客企業の担当者エンティティクラス
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agents")
public class Agent {

	/**
	 * 担当者ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agents_gen")
	@SequenceGenerator(name = "seq_agents_gen", sequenceName = "seq_agents", allocationSize = 1)
	private Integer agentId;
	
	/**
	 * 担当者名
	 */
	@Column
	private String agentName;

	/**
	 * 担当者が所属している部署名
	 */
	@Column
	private String department;

	/**
	 * 担当者が所属しているチーム
	 */
	@Column
	private String team;

	/**
	 * 担当者の役職
	 */
	@Column
	private String position;

	/**
	 * 担当者の電話番号
	 */
	@Column
	private String tel;

	/**
	 * 担当者のメールアドレス
	 */
	@Column
	private String email;

	@Column
	private int deleteFlag;

	
	/**
	 * 担当者と顧客企業情報
	 */
	@ManyToOne
	@JoinColumn(name = "com_id", referencedColumnName = "comId")
	@JsonIgnore
	private Company company;

	/**
	 * 担当者の案件情報
	 */
	@OneToMany(mappedBy = "agent")
	private List<AgendaEntry> agendaEntries;

}
