package service;

import dao.DAOInterfaces.AccountRepository;
import dao.DAOInterfaces.MaterialRepository;
import dao.DAOInterfaces.MaterialUsageRepository;
import dao.DAO_Type;
import dao.tables.Account;
import dao.tables.Material;
import dao.tables.MaterialUsage;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.beans.Customizer;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Service {
    private static Service service = new Service();

    /**
     * login verification
     * @param username username
     * @param password password
     * @return service object if password and username matched, otherwise null
     */
    public static Service connect(String username, String password) {
        AccountRepository accountRepository = (AccountRepository) DAO_Type.ACCOUNT.getTableRepository();
        Account account = accountRepository.findByAccountName(username);
        if (!account.getPasswordHashValue().equals(password))
            return null;
        else
            return service;
    }

    /**
     * get the material usage between a period of time
     * @param from begging time
     * @param until ending time
     * @return material usage map
     */
    public Map<String, Float> getMaterialUsageBetween(Date from, Date until) {

        return null;
    }

    /**
     * get one kinds of material usage between a period of time
     * @param materialName name of material
     * @param from begging time
     * @param until ending time
     * @return material usage
     */
    public float getMaterialUsageBetween(String materialName, Date from, Date until) {
        return 0.0f;
    }
}
