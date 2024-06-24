package jp.co.sss.management.entity;


import java.time.LocalDate;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Company Entity class
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

	/**
	 * 企業ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_companies_gen")
	@SequenceGenerator(name = "seq_companies_gen", sequenceName = "seq_companies", allocationSize = 1)
	private Integer comId;

	/**
	 * 企業名
	 */
	@Column
	private String comName;

	/**
	 * 企業住所
	 */
	@Column
	private String addr;

	/**
	 * 企業サイトURL
	 */
	@Column
	private String comUrl;

	/**
	 * 企業説明
	 */
	@Column
	private String description;

	/**
	 * 企業情報の登録日程(YYYY/MM/DD HH:mm)
	 */
	@Column(updatable = false)
	private final LocalDate insertDate = LocalDate.now();

	/**
	 * 企業情報の修正日程(YYYY/MM/DD HH:mm)
	 */
	@Column
	private LocalDate updateDate;

	/**
	 * 企業の業界情報(カテゴリ)
	 */
	@ManyToOne
	@JoinColumn(name = "cate_id", referencedColumnName = "cateId")
	private ComCategory comCagetory;

	/**
	 * 企業の案件の担当者リスト
	 */
	@OneToMany(mappedBy = "company")
	private List<Agent> agents;

}
