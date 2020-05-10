package service;

import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.tables.Account;
import dao.tables.Material;
import dao.tables.Recipe;
import dao.tables.Stall;

//import controller.model.Recipe;

import java.sql.Date;
import java.util.*;

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
    //admin services

    /**
     * insert or update a material row
     * @param name material name
     * @param types material type
     * @param unitPrice material price
     * @param availablePeriod material available time
     * @return entity been inserted/updated
     * @throws IllegalRequestException
     */
    public Material saveMaterial(String name, MaterialTypes types, float unitPrice, int availablePeriod) throws IllegalRequestException {
        return saveMaterial(EntityFactor.getMaterial(name, types, unitPrice, availablePeriod));
    }

    public Material saveMaterial(Material material) throws IllegalRequestException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        return materialRepository.saveAndFlush(material);
    }

    /**
     * remove a material
     * @param name material name
     * @return material entity been removed
     * @throws IllegalRequestException
     */
    public Material removeMaterial(String name) throws IllegalRequestException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        return materialRepository.deleteByName(name);
    }

    /**
     * insert or update stall row
     * @param stallName stall name
     * @param stallLocation stall location in int number
     * @param stallRent stall rent per month
     * @return entity been inserted/updated
     * @throws IllegalRequestException
     */
    public Stall saveStall(String stallName, int stallLocation, float stallRent) throws IllegalRequestException {
        return saveStall(EntityFactor.getStall(stallName, stallLocation, stallRent));
    }

    public Stall saveStall(Stall stall) throws IllegalRequestException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        StallRepository stallRepository = (StallRepository) DAO_Type.STALL.getTableRepository();
        return stallRepository.save(stall);
    }

    public Stall saveStallWithRecipes(String stallName, int stallLocation, float stallRent, Collection<Recipe> recipes) throws IllegalRequestException {
        return saveStall(EntityFactor.getStallWithRecipes(stallName, stallLocation, stallRent, recipes));
    }

    //
    //material services
    /**
     * get the material usage between a period of time
     * @param from begging time
     * @param until ending time
     * @return material usage map
     * @throws IllegalRequestException
     */
    public Map<String, Float> getALLMaterialUsageBetween(Date from, Date until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

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
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        MaterialUsageRepository materialUsageRepository = (MaterialUsageRepository) DAO_Type.MATERIAL_USAGE.getTableRepository();

        Material material = materialRepository.findByName(materialName);
        return materialUsageRepository.getTotalUsageByTimeBetween(material.getId(), from, until);
    }

    /**
     * get all material names and current amount which below amount
     * @param amount the maximum amount
     * @return map contains material and corresponding amount
     */
    public Map<String, Float> getALLMaterialBelow(float amount) {
        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        List<Material> materials = materialRepository.findALLByAvailableAmountBetween(0, amount);
        Map<String, Float> result = new HashMap<>(materials.size());
        for (Material e : materials) {
            result.put(e.getName(), e.getAvailableAmount());
        }
        return result;
    }
    /* ****************************************************** */
    //stall services
    /**
     * get total sales volume between a period of time
     * @param from begging time
     * @param until ending time
     * @return sales volume
     * @throws IllegalRequestException
     */
    public int getTotalSalesFromTo(Date from, Date until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();

        TransactionRecordRepository transactionRecordRepository = (TransactionRecordRepository) DAO_Type.TRANSACTION_RECORD.getTableRepository();
        return transactionRecordRepository.findALLTotalSalesByTransactionTimeBetween(from, until);
    }
}
