package jp.co.sss.management.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryForm {

	/**
	 * カテゴリーID
	 */
	private Integer cateId;

	/**
	 * カテゴリ名
	 */
	@NotBlank
	private String cateName;

}
