package org.ecommerce.bookreviewapp.dao;

import org.ecommerce.bookreviewapp.entity.Account;
import org.ecommerce.bookreviewapp.model.AccountInfo;

public interface AccountDAO {

    public Account findAccount(String userName );

    public void saveAccount(AccountInfo accountInfo);
}
