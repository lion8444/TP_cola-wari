package jp.co.sss.management.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Agenda Form class
 * 案件
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AgendaForm implements Serializable {

	/**
	 * 案件ID
	 */
	private Integer agendaId;

	/**
	 * 案件タイトル
	 */
	@NotBlank
	@Size(min = 1, max = 30, message = "{text.maxsize.message}")
	private String title;

	/**
	 * 案件の説明
	 */
	@Size(min = 1, max = 2000, message = "{text.maxsize.message}")
	private String description;

	/**
	 * 案件の状態
	 * 0：進行中、1：完了
	 */
	private int status;

	/**
	 * 報告
	 */
	@Size(min = 1, max = 2000, message = "{text.maxsize.message}")
	private String report;

	/**
	 * 契約金
	 */
	private int sales;

}
