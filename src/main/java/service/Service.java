package service;

import dao.DAOInterfaces.AccountRepository;
import dao.DAOInterfaces.MaterialRepository;
import dao.DAOInterfaces.MaterialUsageRepository;
import dao.DAOInterfaces.TransactionRecordRepository;
import dao.DAO_Type;
import dao.tables.Account;
import dao.tables.Material;

import java.sql.Date;
import java.util.*;
import java.util.function.Predicate;

public class Service {
    private final Account account;

    private Service(Account account) {
        this.account = account;
    }

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
            return new Service(account);
    }


    /* ****************************************************** */
    //services
    /**
     * get the material usage between a period of time
     * @param from begging time
     * @param until ending time
     * @return material usage map
     * @throws IllegalRequestException
     */
    public Map<String, Float> getALLMaterialUsageBetween(Date from, Date until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStock() || !account.getAccessInfo().getAccessToOrder()) throw new IllegalRequestException();

        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        MaterialUsageRepository materialUsageRepository = (MaterialUsageRepository) DAO_Type.MATERIAL_USAGE.getTableRepository();

        List<Material> materials = materialRepository.findAll();
        Map<String, Float> result = new HashMap<>(materials.size());
        for (Material e : materials) {
            result.put(e.getName(), materialUsageRepository.getTotalUsageByTimeBetween(e.getId(), from, until));
        }
        return result;
    }

    /**
     * get one kinds of material usage between a period of time
     * @param materialName name of material
     * @param from begging time
     * @param until ending time
     * @return material usage
     * @throws IllegalRequestException
     */
    public float getMaterialUsageBetween(String materialName, Date from, Date until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStock() || !account.getAccessInfo().getAccessToOrder()) throw new IllegalRequestException();

        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        MaterialUsageRepository materialUsageRepository = (MaterialUsageRepository) DAO_Type.MATERIAL_USAGE.getTableRepository();

        Material material = materialRepository.findByName(materialName);
        return materialUsageRepository.getTotalUsageByTimeBetween(material.getId(), from, until);
    }

    /**
     * get total sales volume between a period of time
     * @param from begging time
     * @param until ending time
     * @return sales volume
     * @throws IllegalRequestException
     */
    public int getTotalSalesFromTo(Date from, Date until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToOrder()) throw new IllegalRequestException();

        TransactionRecordRepository transactionRecordRepository = (TransactionRecordRepository) DAO_Type.TRANSACTION_RECORD.getTableRepository();
        return transactionRecordRepository.findALLTotalSalesByTransactionTimeBetween(from, until);
    }
}
