package jp.co.sss.management.form;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
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
public class CompanyForm implements Serializable {

	/**
	 * 企業ID
	 */
	private Integer comId;

	/**
	 * 企業名
	 */
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String comName;

	/**
	 * 企業注所
	 */
	@Size(min = 1, max = 100, message = "{text.maxsize.message}")
	private String addr;

	/**
	 * 企業サイトURL
	 */
	@Size(min = 1, max = 50, message = "{text.maxsize.message}")
	private String comUrl;

	/**
	 * 企業説明
	 */
	@Size(min = 1, max = 2000, message = "{text.maxsize.message}")
	private String description;

	/**
	 * 企業のID
	 */
	private Integer cateId;

	/**
	 * 企業の業界情報(カテゴリ)
	 */
	private String cateName;

}
