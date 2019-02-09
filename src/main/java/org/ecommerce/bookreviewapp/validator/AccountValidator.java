package org.ecommerce.bookreviewapp.validator;

import org.ecommerce.bookreviewapp.model.AccountInfo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz == AccountInfo.class;
	}

	public void validate(Object target, Errors errors) {
		AccountInfo accountInfo = (AccountInfo) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.accountForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.accountForm.password");

        String password = accountInfo.getPassword();
        if (password == null && password.length() < 8) {
        	errors.rejectValue("password", "Pattern.accountForm.password");
        }
	}

}
