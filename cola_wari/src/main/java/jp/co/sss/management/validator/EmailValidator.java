package jp.co.sss.management.validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.sss.management.annotation.EmailCheck;
import jp.co.sss.management.entity.User;
import jp.co.sss.management.repository.UserRepository;

/**
 * メールアドレス重複チェックの独自検証クラス
 *
 * @author System Shared
 */
public class EmailValidator implements ConstraintValidator<EmailCheck, Object> {
	private String email;
	private String userId;

	@Autowired
	UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Override
	public void initialize(EmailCheck annotation) {
		this.email = annotation.fieldEmail();
		this.userId = annotation.fieldId();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		boolean isValidFlg = false;

		String emailProp = (String) beanWrapper.getPropertyValue(this.email);
		Integer idProp = (Integer) beanWrapper.getPropertyValue(this.userId);
		User user = userRepository.findByEmail(emailProp);

		if (user == null || user.getUserId() == idProp) {
			//会員が存在していないもしくは、メールアドレス利用者と会員IDが一致の場合 有効
			isValidFlg = true;
		} else {
			//会員が存在している、
			isValidFlg = false;
		}
		return isValidFlg;
	}

}
