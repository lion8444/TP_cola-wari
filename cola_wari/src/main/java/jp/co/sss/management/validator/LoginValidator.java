package jp.co.sss.management.validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.sss.management.annotation.LoginCheck;
import jp.co.sss.management.bean.UserBean;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.repository.UserRepository;

/**
 * ログインチェックの独自検証クラス
 *
 * @author System Shared
 */
public class LoginValidator implements ConstraintValidator<LoginCheck, Object> {
	private String email;
	private String password;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Override
	public void initialize(LoginCheck annotation) {
		this.email = annotation.fieldEmail();
		this.password = annotation.fieldPassword();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		boolean isValidFlg = false;
		String emailProp = (String) beanWrapper.getPropertyValue(this.email);
		String passwordProp = (String) beanWrapper.getPropertyValue(this.password);

		User user = userRepository.findByEmailAndStatus(emailProp, 0);

		if (user != null && passwordProp.equals(user.getPassword())) {
			UserBean userBean = new UserBean();

			userBean.setEmail(user.getEmail());
			userBean.setUserName(user.getUserName());
			userBean.setAuth(user.getAuth());

			// セッションスコープにログインしたユーザの情報を登録
			session.setAttribute("user", userBean);
			isValidFlg = true;
		} else {
			//ユーザ認証に失敗
			isValidFlg = false;
		}
		return isValidFlg;
	}
}
