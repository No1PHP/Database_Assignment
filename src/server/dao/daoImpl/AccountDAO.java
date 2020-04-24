package server.dao.daoImpl;

import Entity.Account;

import java.util.Collection;

public class AccountDAO extends DAO_handler<Account> {
    public AccountDAO(String url, String dbname, String username, String password) {
        super(url, dbname, username, password);
    }

    @Override
    public int insert(Account obj) {
        return 0;
    }

    @Override
    public int insert(Collection<Account> obj) {
        return 0;
    }

    @Override
    public int delete(Account obj) {
        return 0;
    }

    @Override
    public int delete(Collection<Account> obj) {
        return 0;
    }

    @Override
    public int update(Account obj) {
        return 0;
    }

    @Override
    public int update(Collection<Account> obj) {
        return 0;
    }

    @Override
    public Collection<Account> query(String statement) {
        return null;
    }
}
