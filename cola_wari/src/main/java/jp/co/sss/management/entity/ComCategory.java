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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * company category Entity class
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "com_categories")
public class ComCategory {

	/**
	 * カテゴリID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categories_gen")
	@SequenceGenerator(name = "seq_categories_gen", sequenceName = "seq_com_categories", allocationSize = 1)
	private Integer cateId;
	
	/**
	 * カテゴリ名
	 */
	@Column
	private String cateName;

	/**
	 * カテゴリ内の企業情報
	 */
	@OneToMany(mappedBy = "comCagetory")
	private List<Company> companies;

}
