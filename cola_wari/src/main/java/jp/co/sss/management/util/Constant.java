package jp.co.sss.management.util;

/**
 * 定数定義用クラス
 *
 * @author SystemShared
 */
public class Constant {
	/** 削除フラグの値(未削除状態) */
	public static final int NOT_DELETED = 0;

	/** 削除フラグの値(削除状態) */
	public static final int DELETED = 1;

	/** インデックス番号の初期値 */
	public static final int DEFAULT_INDEX = 1;

	/** 表示順の初期値(新着順) */
	public static final int DEFAULT_SORT_TYPE = 1;

	/** 支払方法初期値(クレジットカード) */
	public static final int DEFAULT_PAYMENT_METHOD = 1;

	/** 戻るフラグの値（戻るボタン押下時） */
	public static final int BACK_FLG_TRUE = 1;

	/** CSS保存用フォルダの名前 */
	public static final String CSS_FOLDER = "/css/";
	/** JS保存用フォルダの名前 */
	public static final String JS_FOLDER = "/js/";

	/** 会員情報の最大登録数 */
	public static final Long USERS_MAX_COUNT = 999999L;
	/** カテゴリ情報の最大登録数 */
	public static final Long CATEGORIES_MAX_COUNT = 99L;

	/** 権限の値(一般部員) */
	public static final int AUTH_NOMAL = 0;
	/** 権限の値(管理者) */
	public static final int AUTH_ADMIN = 1;

}
