package jp.co.sss.management.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * User Bean class
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserCheckedBean {
	
	private UserBean userBean;

	private int checked = 0;

}
