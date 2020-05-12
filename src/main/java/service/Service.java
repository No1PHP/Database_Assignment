package service;

import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;
import org.springframework.data.jpa.repository.Modifying;
import service.exceptions.IllegalRequestException;
import service.exceptions.RestrictedOperationException;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

public class Service {
    private static final ScheduleRecordRepository scheduleRecordRepository = (ScheduleRecordRepository) DAO_Type.SCHEDULE_RECORD.getTableRepository();
    private static final MaterialOrderRepository materialOrderRepository = (MaterialOrderRepository) DAO_Type.MATERIAL_ORDER.getTableRepository();
    private static final OperationRecordRepository operationRecordRepository = (OperationRecordRepository) DAO_Type.OPERATION_RECORD.getTableRepository();
    private static final AccountRepository accountRepository = (AccountRepository) DAO_Type.ACCOUNT.getTableRepository();
    private static final StaffRepository staffRepository = (StaffRepository) DAO_Type.STAFF.getTableRepository();
    private static final AccessInfoRepository accessInfoRepository = (AccessInfoRepository) DAO_Type.ACCESS_INFO.getTableRepository();
    private static final TransactionRecordRepository transactionRecordRepository = (TransactionRecordRepository) DAO_Type.TRANSACTION_RECORD.getTableRepository();
    private static final MaterialUsageRepository materialUsageRepository = (MaterialUsageRepository) DAO_Type.MATERIAL_USAGE.getTableRepository();
    private static final RecipeRepository recipeRepository = (RecipeRepository) DAO_Type.RECIPE.getTableRepository();
    private static final MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
    private static final StallRepository stallRepository = (StallRepository) DAO_Type.STALL.getTableRepository();

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
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Material material = materialRepository.findByName(name);
        if (material == null)
            return materialRepository.saveAndFlush(EntityFactor.getMaterial(name, types, unitPrice, availablePeriod));
        else {
            material.setType((byte) types.ordinal());
            material.setUnitPrice(unitPrice);
            material.setAvailablePeriod(availablePeriod);
            return materialRepository.saveAndFlush(material);
        }
    }

    /**
     * remove a material
     * @param name material name
     * @return material entity been removed
     * @throws IllegalRequestException
     */
    public void removeMaterial(String name) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        Material material = materialRepository.findByName(name);
        if (material.getRecipes().size() >= 1) throw new RestrictedOperationException("material "+name+" still has relevant recipe!");

        materialRepository.deleteByName(name);
        materialRepository.flush();
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
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null)
            return stallRepository.saveAndFlush(EntityFactor.getStall(stallName, stallLocation, stallRent));
        else {
            stall.setStallLocation(stallLocation);
            stall.setStallRent(stallRent);
            return stallRepository.saveAndFlush(stall);
        }
    }

    public Stall saveStallWithRecipes(String stallName, int stallLocation, float stallRent, Collection<Recipe> recipes) throws IllegalRequestException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        return stallRepository.saveAndFlush(EntityFactor.getStallWithRecipes(stallName, stallLocation, stallRent, recipes));
    }

    /**
     * remove a stall row
     * @param name stall name
     */
    public void removeStall(String name) throws IllegalRequestException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Stall stall = stallRepository.findByStallName(name);
        Set<Recipe> recipes = stall.getRecipes();
        for (Recipe e : recipes)
            e.getStalls().remove(stall);
        recipeRepository.saveAll(recipes);
        stall.getRecipes().clear();
        stallRepository.saveAndFlush(stall);
        stallRepository.delete(stall);
        stallRepository.flush();
    }

    /**
     * insert a staff
     * @param staffName staff name
     * @param types staff working type
     * @param start start working time in a day
     * @param end end working time in a day
     * @return staff entity
     * @throws IllegalRequestException
     * @throws RestrictedOperationException
     */
    public Staff insertStaff(String staffName, StaffCategoryTypes types, Time start, Time end) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        if (staffName.equals("none") || staffName.equals("admin") || types == StaffCategoryTypes.ADMIN) throw new RestrictedOperationException("cannot insert this staff!");

        return staffRepository.saveAndFlush(EntityFactor.getStaff(staffName, types, start, end));
    }

    /**
     * update a staff
     * @param types staff working type
     * @param start start working time in a day
     * @param end end working time in a day
     * @return staff entity
     * @throws IllegalRequestException
     * @throws RestrictedOperationException
     */
    public Staff updateStaff(int id, StaffCategoryTypes types, Time start, Time end) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        Staff staff = staffRepository.findByStaffID(id);
        if (staff.getStaffName().equals("none") || staff.getStaffName().equals("admin"))
            throw new RestrictedOperationException("cannot update this staff!");

        staff.setStaffCategory((byte) types.ordinal());
        staff.setTimeStartWorking(start);
        staff.setTimeEndWorking(end);
        return staffRepository.saveAndFlush(staff);
    }

    /**
     * remove a staff
     * @param id staff id
     * @throws IllegalRequestException
     * @throws RestrictedOperationException
     */
    public void removeStaff(int id) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        Staff staff = staffRepository.findByStaffID(id);
        if (staff.getStaffName().equals("none") || staff.getStaffCategory() == StaffCategoryTypes.ADMIN.ordinal())
            throw new RestrictedOperationException("cannot delete this staff!");

        Set<OperationRecord> operationRecords = staff.getOperationRecords();
        Staff none = staffRepository.findALLByStaffName("none").get(0);
        for (OperationRecord e : operationRecords) {
            e.setStaff(none);
            none.getOperationRecords().add(e);
        }
        operationRecordRepository.saveAll(operationRecords);
        operationRecordRepository.flush();
        staffRepository.saveAndFlush(none);
        staffRepository.delete(staff);
        staffRepository.flush();
    }

    /**
     * update or insert a account row
     * @param staff master of account
     * @param accessInfo account access type
     * @param accountName account username
     * @param passwordHashValue account password
     * @return account entity
     * @throws IllegalRequestException
     * @throws RestrictedOperationException
     */
    public Account saveAccount(Staff staff, AccessInfo accessInfo, String accountName, String passwordHashValue) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        if (staff.getStaffName().equals("none") || staff.getStaffName().equals("admin"))
            throw new RestrictedOperationException("can't update account for this staff!");

        Account account = accountRepository.findByStaffID(staff.getStaffID());
        if (account == null) {
            account = EntityFactor.getAccount(staff, accessInfo, accountName, passwordHashValue);
        } else {
            account.setAccessInfo(accessInfo);
            account.setAccountName(accountName);
            account.setPasswordHashValue(passwordHashValue);
        }
        return accountRepository.saveAndFlush(account);
    }

    public void updateAdminAccount(String passwordHashValue) throws IllegalRequestException {

    }

    public AccessInfo saveAccessInfo(String position, boolean accessToMaterial, boolean accessToStaff, boolean accessToStall) throws IllegalRequestException, RestrictedOperationException {
        return null;
    }

    public AccessInfo removeAccessInfo(String position) throws IllegalRequestException, RestrictedOperationException {
        return null;
    }
    //
    //material services

    public float getMaterialAvailableAmount(String materialName) throws IllegalRequestException {
        return 0;
    }

    /**
     * get the material usage between a period of time
     * @param from begging time
     * @param until ending time
     * @return material usage map
     * @throws IllegalRequestException
     */
    public Map<String, Float> getALLMaterialUsageBetween(Timestamp from, Timestamp until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        List<Material> materials = materialRepository.findAll();
        Map<String, Float> result = new HashMap<>(materials.size());
        for (Material e : materials) {
            result.put(e.getName(), materialUsageRepository.getTotalUsageByTimeBetween(e.getName(), from, until));
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
    public float getMaterialUsageBetween(String materialName, Timestamp from, Timestamp until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Material material = materialRepository.findByName(materialName);
        return materialUsageRepository.getTotalUsageByTimeBetween(material.getName(), from, until);
    }

    public Map<String, Float> getMaterialUsagePrediction() throws IllegalRequestException {
        return null;
    }

    public float getMaterialUsagePrediction(String materialName) throws IllegalRequestException {
        return 0;
    }

    /**
     * get all material names and current amount which below amount
     * @param amount the maximum amount
     * @return map contains material and corresponding amount
     */
    public Map<String, Float> getALLMaterialBelow(float amount) {
        List<Material> materials = materialRepository.findALLByAvailableAmountBetween(0, amount);
        Map<String, Float> result = new HashMap<>(materials.size());
        for (Material e : materials) {
            result.put(e.getName(), e.getAvailableAmount());
        }
        return result;
    }

    public MaterialOrder orderMaterial(String note, String materialName, float materialAmount) throws IllegalRequestException {
        return null;
    }

    public List<MaterialOrder> findMaterialOrder(String materialName) throws IllegalRequestException {
        return null;
    }

    public MaterialOrder ensureMaterialOrder(MaterialOrder order) throws IllegalRequestException {
        return null;
    }

    public List<MaterialOrder> findMaterialOrderOutOfDate(int limit) throws IllegalRequestException {
        return null;
    }

    public List<MaterialOrder> findMaterialOrderOutOfDate(String materialName, int limit) throws IllegalRequestException {
        return null;
    }
    /* ****************************************************** */
    //stall services

    public Stall addRecipeForStall(String stallName, List<Recipe> recipes) throws IllegalRequestException {
        return null;
    }

    public List<Object[]> getRecipeInOrderBySellingDuring(Date from, Date to) throws IllegalRequestException {
        return null;
    }

    /**
     * get total sales volume between a period of time
     * @param from begging time
     * @param until ending time
     * @return sales volume
     * @throws IllegalRequestException
     */
    public int getTotalSalesDuring(Timestamp from, Timestamp until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();

        return transactionRecordRepository.findALLTotalSalesByTransactionTimeBetween(from, until);
    }

    public int getTotalSalesDuring(String stallName, Timestamp from, Timestamp until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();

        return transactionRecordRepository.findALLTotalSalesByTransactionTimeBetween(from, until);
    }

    public List<Object[]> getStallInOrderBySellingBetweenDate(Date from, Date to) throws IllegalRequestException {
        return null;
    }

    public Map<Material, Float> getStallUsageDuring(Date from, Date to) throws IllegalRequestException {
        return null;
    }

    public float getStallUsageDuring(String materialName, Date from, Date to) throws IllegalRequestException {
        return 0;
    }

    public float getStallProfitDuring(String stallName, Date from, Date to) throws IllegalRequestException {
        return 0;
    }

    public float getStallRent(String stallName) throws IllegalRequestException {
        return 0;
    }

    public List<TransactionRecord> getTransactionRecordDuring(String stall, Date from, Date to) throws IllegalRequestException {
        return null;
    }
    /* ****************************************************** */
    //staff services
    public Staff getStaffByID(int id) throws IllegalRequestException {
        return null;
    }

    public StaffCategoryTypes getStaffCategoryTypes(int id) throws IllegalRequestException {
        return null;
    }

    public List<OperationRecord> getOperationRecord(int id) throws IllegalRequestException {
        return null;
    }

    public List<ScheduleRecord> getScheduleRecord(int id, boolean showFinished) throws IllegalRequestException {
        return null;
    }

    public boolean scheduleStaff(Timestamp start, Timestamp end, Staff targetStaff, String note) throws IllegalRequestException, RestrictedOperationException {
        return false;
    }
    /* ****************************************************** */
    //general services

    public int getOwnID() {
        return 0;
    }

    public StaffCategoryTypes getOwnCategoryType() {
        return null;
    }

    public List<OperationRecord> getOwnOperationRecord() {
        return null;
    }

    public List<ScheduleRecord> getOwnScheduleRecord(boolean showFinished) {
        return null;
    }

    public boolean getMaterialForStall(String materialName, String stallName, float amount) {
        return false;
    }

    public TransactionRecord stallSell(String recipeName, String stallName, int amount, float price) {
        return null;
    }
}
