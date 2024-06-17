package jp.co.sss.management.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ページング機能処理クラス
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@PropertySource("classpath:page.properties")
public class PageNavigator {
	/**
	 * ページごとのリスト数
	 */
	@Value("${custom.board.page}")
	private int countPerPage;

	/**
	 * グループごとのページ数
	 */
	@Value("${custom.board.group}")
	private int pagePerGroup;

	/**
	 * 現在ページ
	 * 最新情報(レコード)が1から始まる
	 */
	private int currentPage;

	/**
	 * 全体情報(レコード)数
	 */
	private int totalRecordsCount;

	/**
	 * 全体ページ数
	 */
	private int totalPageCount;

	/**
	 * 現在グループ
	 * 最新グループが0から始まる
	 */
	private int currentGroup;

	/**
	 * 現在グループの最初ページ
	 */
	private int startPageGroup;

	/**
	 * 現在グループの最後ページ
	 */
	private int endPageGroup;

	/**
	 * 全体情報(レコード)の中で現在ページの最初レコードの位置
	 * 0から始まる
	 */
	private int startRecord;
	

	/**
	 * コンストラクタ
	 * @param pagePerGroup　グループごとのページ数
	 * @param countPerPage　ページごとのレコード数
	 * @param currentPage　現在ページ
	 * @param totalRecordsCount　全体レコード数
	 */
	public PageNavigator(int currentPage, int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
		
		// 全体ページ数
		totalPageCount = (totalRecordsCount + countPerPage - 1) / countPerPage;

		// 受けた現在ページが1より小さい場合、現在ページを1にする
		if (currentPage < 1)	currentPage = 1;
		// 受けた現在ページが最後ページより大きい場合、現在ページを最後ページにする
		if (currentPage > totalPageCount)	currentPage = totalPageCount;
		
		this.currentPage = currentPage;

		// 現在グループ
		currentGroup = (currentPage - 1) / pagePerGroup;
		
		// 現在グループの最初ページ
		startPageGroup = currentGroup * pagePerGroup + 1;
		// 現在グループの最初ページが1より小さい場合、現在グループの最初ページを1にする
		startPageGroup = startPageGroup < 1 ? 1 : startPageGroup;
		// 現在グループの最後ページ
		endPageGroup = startPageGroup + pagePerGroup - 1;
		// 現在グループの最後ページが全体ページ数より小さい場合、全体ページを最後ページにする
		endPageGroup = endPageGroup < totalPageCount ? endPageGroup : totalPageCount;

		// 全体レコードの中で現在ページの最初レコードの位置
		startRecord = (currentPage - 1) * countPerPage;			
	}
}