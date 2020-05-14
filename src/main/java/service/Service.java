package service;

import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import service.exceptions.IllegalRequestException;
import service.exceptions.RestrictedOperationException;

import javax.persistence.criteria.*;
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
        if (account == null || !account.getPasswordHashValue().equals(password))
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
     * @throws IllegalRequestException current account doesn't have the permission
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
     * @throws IllegalRequestException current account doesn't have the permission
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
     * @throws IllegalRequestException current account doesn't have the permission
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
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
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
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
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
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
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
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
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

    /**
     * insert or update an access info row
     * @param position access info type name
     * @param accessToMaterial access permission to material
     * @param accessToStaff access permission to staff
     * @param accessToStall access permission to stall
     * @return access info entity
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public AccessInfo saveAccessInfo(String position, boolean accessToMaterial, boolean accessToStaff, boolean accessToStall) throws IllegalRequestException, RestrictedOperationException {
        if (account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        if (position.equals("none") || position.equals("admin")) throw new RestrictedOperationException("cannot update this access info!");

        AccessInfo accessInfo = accessInfoRepository.findByPosition(position);
        if (accessInfo == null) {
            accessInfo = EntityFactor.getAccessInfo(position, accessToMaterial, accessToStaff, accessToStall);
        } else {
            accessInfo.setAccessToMaterial(accessToMaterial);
            accessInfo.setAccessToStaff(accessToStaff);
            accessInfo.setAccessToStall(accessToStall);
        }
        return accessInfoRepository.saveAndFlush(accessInfo);
    }

    /**
     * remove access info row
     * @param position position name
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public void removeAccessInfo(String position) throws IllegalRequestException, RestrictedOperationException {
        if (account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        if (position.equals("none") || position.equals("admin")) throw new RestrictedOperationException("cannot remove this access info!");

        AccessInfo accessInfo = accessInfoRepository.findByPosition(position);
        if (accessInfo != null) {
            accessInfoRepository.delete(accessInfo);
            accessInfoRepository.flush();
        }
    }
    //
    //material services

    /**
     * get available amount of one material
     * @param materialName material name
     * @return available amount
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public float getMaterialAvailableAmount(String materialName) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Material material = materialRepository.findByName(materialName);
        return (material == null)? 0 : material.getAvailableAmount();
    }

    /**
     * get the material usage between a period of time
     * @param from begging time
     * @param until ending time
     * @return material usage map
     * @throws IllegalRequestException current account doesn't have the permission
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
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public float getMaterialUsageBetween(String materialName, Timestamp from, Timestamp until) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Material material = materialRepository.findByName(materialName);
        return materialUsageRepository.getTotalUsageByTimeBetween(material.getName(), from, until);
    }

    /**
     * get the prediction
     * @return the prediction usage value
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public Map<String, Float> getMaterialUsagePrediction() throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        List<Material> materials = materialRepository.findAll();
        Map<String, Float> result = new HashMap<>(materials.size());
        for (Material e : materials) {
            result.put(e.getName(), getMaterialUsagePrediction(e.getName()));
        }
        return result;
    }

    public float getMaterialUsagePrediction(String materialName) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        return getMaterialUsageBetween(materialName, new Timestamp(System.currentTimeMillis() - 2592000),  new Timestamp(System.currentTimeMillis()));
    }

    /**
     * get all material names and current amount which below amount
     * @param amount the maximum amount
     * @return map contains material and corresponding amount
     */
    public Map<String, Float> getALLMaterialBelow(float amount) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        List<Material> materials = materialRepository.findALLByAvailableAmountBetween(0, amount);
        Map<String, Float> result = new HashMap<>(materials.size());
        for (Material e : materials) {
            result.put(e.getName(), e.getAvailableAmount());
        }
        return result;
    }

    /**
     * make order for materail
     * @param note comments
     * @param materialName material name
     * @param materialAmount order amount
     * @return material order entity
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public MaterialOrder orderMaterial(String note, String materialName, float materialAmount) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Material material = materialRepository.findByName(materialName);
        if (material == null) throw new RestrictedOperationException("the material doesn't exist!");

        return materialOrderRepository.saveAndFlush(EntityFactor.getMaterialOrder(account.getStaff(), note, material, materialAmount));
    }

    /**
     * find orders of a material
     * @param materialName material name
     * @param from starting time
     * @param to ending time
     * @return order list
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public List<MaterialOrder> findMaterialOrder(String materialName, Timestamp from, Timestamp to) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Material material = materialRepository.findByName(materialName);
        if (material == null) throw new RestrictedOperationException("the material doesn't exist!");

        Specification<MaterialOrder> specification = (Specification<MaterialOrder>) (root, query, criteriaBuilder) -> {
            Path<Material> material1 = root.get("material");
            Path<OperationRecord> operationRecord = root.get("orderRecord");

            Predicate equalName = criteriaBuilder.equal(material1.<String>get("name"), materialName);
            Predicate betweenTime = criteriaBuilder.between(operationRecord.get("operationTime"), from, to);
            return criteriaBuilder.and(equalName, betweenTime);
        };
        return materialOrderRepository.findAll(specification);
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


    public Map<String,Object> addRecipe(Integer recipeID, String recipeName, String relevantIngredient, float price){
        return null;
    }

    public Recipe saveRecipe(Integer recipeID, String recipeName, String relevantIngredient, float price){
        return null;
    }

    public Recipe removeRecipe(Integer recipeID, String recipeName, String relevantIngredient, float price){
        return null;
    }
    /**
     * @author Zhining
     * @description
     * @param newPassword
     * @return
     * @create 2020/5/12 8:59 上午
     * @throws IllegalRequestException
     **/
    public String changePassword(String newPassword){
        account.setPasswordHashValue(newPassword);
        return String.valueOf(accountRepository.saveAndFlush(account));
    }

}
