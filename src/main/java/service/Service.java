package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.JSONAble;
import dao.enums.MaterialTypes;
import dao.enums.OperationType;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import service.exceptions.IllegalRequestException;
import service.exceptions.RestrictedOperationException;

import javax.persistence.criteria.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@org.springframework.stereotype.Service
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
    public Material saveMaterial(String name, MaterialTypes types, float unitPrice, float availablePeriod) throws IllegalRequestException {
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
    public Material removeMaterial(String name) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();
        Material material = materialRepository.findByName(name);
        Collection<Recipe> recipes = recipeRepository.findALLByMaterialsContains(material);
        if (recipes.size() >= 1) throw new RestrictedOperationException("material "+name+" still has relevant recipe!");

        materialRepository.delete(material);
        materialRepository.flush();
        return material;
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

    public Stall saveStall(String stallName, float stallRent, float costLastMonth) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null)
            throw new RestrictedOperationException("no such stall!");
        else {
            stall.setStallRent(stallRent);
            stall.setCostLastMonth(costLastMonth);
            return stallRepository.saveAndFlush(stall);
        }
    }

    public Stall saveStallWithRecipes(String stallName, String... recipes) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null)
            throw new RestrictedOperationException("no such stall!");
        Collection<Recipe> recipeCollection = new LinkedList<>();
        for (String e : recipes) {
            recipeCollection.add(recipeRepository.findByRecipeName(e));
        }
        stall.getRecipes().addAll(recipeCollection);
        return stallRepository.saveAndFlush(stall);
    }

    public Stall saveStallLocation(String stallName, int stallLocation) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null)
            throw new RestrictedOperationException("no such stall!");
        String message = "change stall " + stallName + " from " + stall.getStallLocation() + " to " + stallLocation;
        operationRecordRepository.saveAndFlush(EntityFactor.getOperationRecord(OperationType.STALL_CHANGE, account.getStaff(), true, message));
        stall.setStallLocation(stallLocation);
        return stallRepository.saveAndFlush(stall);
    }

    /**
     * remove a stall row
     * @param name stall name
     */
    public void removeStall(String name) throws IllegalRequestException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Stall stall = stallRepository.findByStallName(name);
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

        List<OperationRecord> operationRecords = operationRecordRepository.findALLByStaffID(staff.getStaffID());
        Staff none = staffRepository.findALLByStaffName("none").get(0);
        for (OperationRecord e : operationRecords) {
            e.setStaff(none);
        }
        operationRecordRepository.saveAll(operationRecords);
        operationRecordRepository.flush();
        staffRepository.delete(staff);
        staffRepository.flush();
    }

    /**
     * update or insert a account row
     * @param staffId master of account
     * @param position account access type
     * @param accountName account username
     * @param passwordHashValue account password
     * @return account entity
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public Account saveAccount(int staffId, String position, String accountName, String passwordHashValue) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getPosition().equals("admin")) throw new IllegalRequestException();

        Staff staff = staffRepository.findByStaffID(staffId);
        AccessInfo accessInfo = accessInfoRepository.findByPosition(position);
        if (staff.getStaffName().equals("none") || staff.getStaffName().equals("admin"))
            throw new RestrictedOperationException("can't update account for this staff!");
        if (accessInfo == null)
            throw new RestrictedOperationException("position doesn't exist!");

        Account account = staff.getAccount();
        if (account == null) {
            account = EntityFactor.getAccount(staff, accessInfo, accountName, passwordHashValue);
        } else {
            account.setAccessInfo(accessInfo);
            account.setAccountName(accountName);
            account.setPasswordHashValue(passwordHashValue);
        }
        return accountRepository.saveAndFlush(account);
    }
    //
    //material services

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

        return getMaterialUsageBetween(materialName, new Timestamp(System.currentTimeMillis() - 2592000000L),  new Timestamp(System.currentTimeMillis()+1000));
    }

    /**
     * get all material names and current amount which below amount
     * @param amount the maximum amount
     * @return map contains material and corresponding amount
     */
    public Map<String, Float> getALLMaterialBelow(float amount) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Map<String, Float> result = new HashMap<>();
        List<Material> materials = materialRepository.findAll();
        for (Material e : materials) {
            float available = getMaterialAvailableAmount(e.getName());
            if (available < amount) result.put(e.getName(), available);
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
     * @Query (value = "from MaterialOrder as Order " +
     *            "inner join Material on Order.materialName = Material.name " +
     *            "inner join OperationRecord as Oper on Order.operationOrderID = Oper.operationID " +
     *            "where Material.name = ?1 and Oper.operationTime >= ?2 and Oper.operationTime <= ?3")
     */
    public List<MaterialOrder> findMaterialOrder(String materialName, Timestamp from, Timestamp to) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        Material material = materialRepository.findByName(materialName);
        if (material == null) throw new RestrictedOperationException("the material doesn't exist!");

        Specification<MaterialOrder> specification = (Specification<MaterialOrder>) (root, query, criteriaBuilder) -> {
            Path<String> name = root.join("material").get("name");
            Path<Timestamp> time = root.join("orderRecord").get("operationTime");

            Predicate equalName = criteriaBuilder.equal(name, materialName);
            Predicate betweenTime = criteriaBuilder.between(time, from, to);
            return criteriaBuilder.and(equalName, betweenTime);
        };
        return materialOrderRepository.findAll(specification);
    }

    /**
     * ensure the material is stored
     * @param orderID the order id been ensured
     * @param note comment
     * @return order entity
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public MaterialOrder ensureMaterialOrder(int orderID, String note) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        MaterialOrder materialOrder = materialOrderRepository.findByOperationOrderID(orderID);
        if (materialOrder == null) throw new RestrictedOperationException("no such order!");
        if (materialOrder.getOperationStorageID() != null) throw new RestrictedOperationException("already stored!");

        materialOrderRepository.saveAndFlush(EntityFactor.confirmMaterialOrder(account.getStaff(), note, materialOrder));
        return null;
    }

    /**
     * find the material orders that is going to out of date
     * @param limit time limit before out of date
     * @return material order list
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public List<MaterialOrder> findMaterialOrderOutOfDate(int limit) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        List<MaterialOrder> result = new LinkedList<>();
        List<Material> materialList = materialRepository.findAll();
        for (Material e : materialList) {
            result.addAll(findMaterialOrderOutOfDate(e, limit));
        }
        return result;
    }

    /**
     * find the material orders that is going to out of date
     * @param materialName material name
     * @param limit time limit before out of date
     * @return material order list
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public List<MaterialOrder> findMaterialOrderOutOfDate(String materialName, int limit) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial()) throw new IllegalRequestException();

        return findMaterialOrderOutOfDate(materialRepository.findByName(materialName), limit);
    }

    public List<MaterialOrder> findMaterialOrderOutOfDate(Material material, int limit) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToMaterial() || material == null) throw new IllegalRequestException();

        Timestamp timeLimit = getTimeLimit(material, limit);
        return materialOrderRepository.findAllOutOfDate(material.getName(), timeLimit);
    }

    private Timestamp getTimeLimit(Material material, int limit) {
        return new Timestamp((long) (System.currentTimeMillis() - 86400000L * (material.getAvailablePeriod() - limit)));
    }
    /* ****************************************************** */
    //stall services

    /**
     * add recipes for stall
     * @param stallName stall name
     * @param recipes recipe names
     * @return stall entity
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public Stall addRecipeForStall(String stallName, Iterable<String> recipes) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null) throw new RestrictedOperationException("no such stall!");

        for (String e : recipes)
            stall.getRecipes().add(recipeRepository.findByRecipeName(e));
        stallRepository.saveAndFlush(stall);
        return stall;
    }
    public Stall addRecipeForStall(String stallName, String... recipes) throws IllegalRequestException, RestrictedOperationException {
        return addRecipeForStall(stallName, Arrays.asList(recipes));
    }

    public Stall removeRecipeFromStall(String stallName, Collection<String> recipes) throws IllegalRequestException, RestrictedOperationException  {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null) throw new RestrictedOperationException("no such stall!");

        stall.getRecipes().removeIf(recipe -> recipes.contains(recipe.getRecipeName()));
        stallRepository.saveAndFlush(stall);
        return stall;
    }

    public Stall removeRecipeFromStall(String stallName, String... recipes) throws IllegalRequestException, RestrictedOperationException  {
        return removeRecipeFromStall(stallName, Arrays.asList(recipes));
    }

    /**
     * get the selling order of recipe during period of time
     * @param from starting time
     * @param to ending time
     * @return material entity and the corresponding selling amount in type Float
     * @throws IllegalRequestException current account doesn't have the permission
     *
     * select Recipe.recipeName as name, sum(T.numbers) as total from Recipe
     * left join TransactionRecord as T using(recipeName)
     * where T.transactionTime >= from and t.transactionTime <= to
     * group by name
     * order by total
     */
    public List<Object[]> getRecipeInOrderBySellingDuring(Timestamp from, Timestamp to) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        return recipeRepository.findSalesOrderDuring(from, to);
    }

    public int findSalesDuring30Day(String name) {
        Integer result = recipeRepository.findSalesDuring(name, new Timestamp(System.currentTimeMillis() - 30*86400000L), new Timestamp(System.currentTimeMillis()));
        return result == null ? 0 : result;
    }

    public int getTotalSalesDuring(String stallName, Timestamp from, Timestamp until) throws RestrictedOperationException {
        if (stallRepository.findByStallName(stallName) == null) throw new RestrictedOperationException("no such stall!");

        Integer result = transactionRecordRepository.findALLTotalSalesByStallNameAndTransactionTimeBetween(stallName, from, until);
        return (result == null)? 0 : result;
    }

    /**
     * get the selling order of stall during period of time
     * @param from starting time
     * @param to ending time
     * @return stall entity and the corresponding selling amount in type Float
     * @throws IllegalRequestException current account doesn't have the permission
     *
     * select Stall*, sum(T.numbers) as total from Stall
     * left join TransactionRecord as T using(stallName)
     * where T.transactionTime >= from and t.transactionTime <= to
     * group by name
     * order by total
     */
    public List<Object[]> getStallInOrderBySellingBetweenDate(Timestamp from, Timestamp to) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        return stallRepository.findSalesDuring(from, to);
    }

    /**
     * get the total material usage from stall
     * @param from starting time
     * @param to ending time
     * @return result list
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public List<Object[]> getStallUsageDuring(Timestamp from, Timestamp to) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();

        return materialUsageRepository.getUsageByTimeBetween(from, to);
    }

    public List<Object[]> getStallUsageDuring(String stallName, Timestamp from, Timestamp to) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();

        return materialUsageRepository.getUsageByTimeBetween(stallName, from, to);
    }

    /**
     * get the profit of a stall
     * @param stallName stall name
     * @param from starting time
     * @param to ending time
     * @return total profit
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public float getStallProfitDuring(String stallName, Timestamp from, Timestamp to) throws RestrictedOperationException {
        if (stallRepository.findByStallName(stallName) == null) throw new RestrictedOperationException("no such stall!");

        Float result = transactionRecordRepository.getProfitOfStallDuring(stallName, from, to);
        return (result == null)? 0 : result;
    }

    public TransactionRecord saveTransactionRecord(int amount, float price, String recipeName, String stallName) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        Stall stall = stallRepository.findByStallName(stallName);
        Recipe recipe = recipeRepository.findByRecipeName(recipeName);
        if (stall == null || recipe == null) throw new RestrictedOperationException("no such stall or recipe!");
        TransactionRecord transactionRecord = EntityFactor.getTransactionRecord(amount, price, recipe, stall);
        return transactionRecordRepository.save(transactionRecord);
    }

    /**
     * get all transaction record of a stall during a period of time
     * @param stallName stall name
     * @param from beginning time
     * @param to ending time
     * @return transaction record set
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public List<TransactionRecord> getTransactionRecordDuring(String stallName, Timestamp from, Timestamp to) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        Stall stall = stallRepository.findByStallName(stallName);
        if (stall == null) throw new RestrictedOperationException("no such stall!");
        return transactionRecordRepository.findALLByStallNameAndTransactionTimeBetween(stallName, from, to);
    }

    public void removeTransactionRecord(int id) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        transactionRecordRepository.deleteById(id);
    }

    public Recipe saveRecipe(String recipeName, float price, String... materials) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        if (recipeRepository.findByRecipeName(recipeName) != null) throw new RestrictedOperationException("recipe already exist");
        List<Material> materialList = new LinkedList<>();
        for (String e : materials) {
            Material material = materialRepository.findByName(e);
            if (material != null) materialList.add(material);
        }
        Recipe recipe = EntityFactor.getRecipe(recipeName, price, materialList);
        return recipeRepository.saveAndFlush(recipe);
    }

    public Recipe modifyRecipe(String recipeName, float price, String... materials) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        Recipe recipe = recipeRepository.findByRecipeName(recipeName);
        if (recipe == null) throw new RestrictedOperationException("recipe doesn't exist");
        recipe.getMaterials().clear();
        recipe.setPrice(price);
        for (String e : materials) {
            Material material = materialRepository.findByName(e);
            if (material != null) recipe.getMaterials().add(material);
        }
        return recipeRepository.saveAndFlush(recipe);
    }

    public void removeRecipe(String recipeName) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStall()) throw new IllegalRequestException();
        Recipe recipe = recipeRepository.findByRecipeName(recipeName);
        if (recipe == null) throw new RestrictedOperationException("no such recipe");
        recipeRepository.delete(recipe);
    }
    /* ****************************************************** */
    //staff services

    /**
     * get operation record list by staff id
     * @param id staff id
     * @return operation recode list
     * @throws IllegalRequestException current account doesn't have the permission
     */
    public List<OperationRecord> getOperationRecord(int id) throws IllegalRequestException {
        if (!account.getAccessInfo().getAccessToStaff()) throw new IllegalRequestException();

        return operationRecordRepository.findALLByStaffIDOrderByOperationTime(id);
    }

    /**
     * create a schedule record for a staff
     * @param start start working time
     * @param end end working time
     * @param targetStaffID id of staff to be scheduled
     * @param note comment
     * @throws IllegalRequestException current account doesn't have the permission
     * @throws RestrictedOperationException current operation cannot be applied
     */
    public void scheduleStaff(Timestamp start, Timestamp end, int targetStaffID, String note) throws IllegalRequestException, RestrictedOperationException {
        if (!account.getAccessInfo().getAccessToStaff()) throw new IllegalRequestException();
        Staff staff = staffRepository.findByStaffID(targetStaffID);
        if (staff == null) throw new RestrictedOperationException("no such staff!");

        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(start, end, staff, account.getStaff(), note));
    }
    /* ****************************************************** */
    //general services

    /**
     * get one kinds of material usage between a period of time
     * @param materialName name of material
     * @param from begging time
     * @param until ending time
     * @return material usage
     */
    public float getMaterialUsageBetween(String materialName, Timestamp from, Timestamp until) {
        Material material = materialRepository.findByName(materialName);
        Float result = materialUsageRepository.getTotalUsageByTimeBetween(material.getName(), from, until);
        return (result == null)? 0 : result;
    }

    /**
     * get available amount of one material
     * @param materialName material name
     * @return available amount
     */
    public float getMaterialAvailableAmount(String materialName) {
        Timestamp limit = getTimeLimit(materialRepository.findByName(materialName), 0);
        Float ordered = materialOrderRepository.findAvailableByName(materialName, limit);
        Float used = materialUsageRepository.getUsageOf(materialName, limit);
        return (ordered == null)? 0 : ordered - ((used == null)? 0 : used);
    }

    public JSONArray getALL(String key, int page, int size) {
        Iterable queryResult;
        JSONArray array = new JSONArray();
        PageRequest pageRequest = PageRequest.of(page-1, size);
        switch (key) {
            case "Account":
                queryResult = accountRepository.findAll(pageRequest);
                array = handle(queryResult);
                break;
            case "Material":
                queryResult = materialRepository.findAll(pageRequest);
                array = handleMaterial(queryResult);
                break;
            case "MaterialOrder":
                queryResult = materialOrderRepository.findAll(pageRequest);
                array = handleMaterialOrder(queryResult);
                break;
            case "MaterialUsage":
                queryResult = materialUsageRepository.findAll(pageRequest);
                array = handle(queryResult);
                break;
            case "OperationRecord":
                queryResult = operationRecordRepository.findAll(pageRequest);
                array = handle(queryResult);
                break;
            case "Recipe":
                queryResult = recipeRepository.findAll(pageRequest);
                array = handleRecipe(queryResult);
                break;
            case "ScheduleRecord":
                queryResult = scheduleRecordRepository.findAll(pageRequest);
                array = handle(queryResult);
                break;
            case "Staff":
                queryResult = staffRepository.findAll(pageRequest);
                array = handle(queryResult);
                break;
            case "Stall":
                queryResult = stallRepository.findAll(pageRequest);
                array = handleStall(queryResult);
                break;
            case "Transaction":
                queryResult = transactionRecordRepository.findAll(pageRequest);
                array = handle(queryResult);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
        return array;
    }

    private JSONArray handle(Iterable queryResult) {
        JSONArray array = new JSONArray();
        for (Object e: queryResult) {
            JSONObject json = ((JSONAble)e).getJson();
            array.add(json);
        }
        return array;
    }

    private JSONArray handleMaterial(Iterable queryResult) {
        JSONArray array = new JSONArray();
        for (Object e: queryResult) {
            JSONObject json = ((JSONAble)e).getJson();
            json.put("availableAmount", this.getMaterialAvailableAmount(json.getString("name")));
            json.put("materialUsages", this.getMaterialUsageBetween(json.getString("name"), new Timestamp(0), new Timestamp(System.currentTimeMillis())));
            Collection<Recipe> recipes = recipeRepository.findALLByMaterialsContains((Material) e);
            StringBuilder stringBuilder = new StringBuilder("");
            for (Recipe r : recipes) {
                stringBuilder.append(r.getRecipeName());
                stringBuilder.append(" ");
            }
            json.put("recipes", stringBuilder.toString());
            array.add(json);
        }
        return array;
    }

    private JSONArray handleMaterialOrder(Iterable queryResult) {
        JSONArray array = new JSONArray();
        for (Object e: queryResult) {
            JSONObject json = ((JSONAble)e).getJson();
            Float result = 0f;
            if (json.getInteger("operationStorageID") != null)
                 result = materialOrderRepository.getUsedAmount(json.getInteger("operationStorageID"));
            json.put("usedAmount", result == null ? 0 : result);
            array.add(json);
        }
        return array;
    }

    private JSONArray handleStall(Iterable queryResult) {
        JSONArray array = new JSONArray();
        for (Object e: queryResult) {
            JSONObject json = ((JSONAble)e).getJson();
            json.put("totalSales", this.getTotalSalesDuring(json.getString("stallName"), new Timestamp(System.currentTimeMillis() - 30*86400000L), new Timestamp(System.currentTimeMillis())));
            json.put("profit", this.getStallProfitDuring(json.getString("stallName"), new Timestamp(System.currentTimeMillis() - 30*86400000L), new Timestamp(System.currentTimeMillis())));
            array.add(json);
        }
        return array;
    }

    private JSONArray handleRecipe(Iterable queryResult) {
        JSONArray array = new JSONArray();
        for (Object e: queryResult) {
            JSONObject json = ((JSONAble)e).getJson();
            json.put("totalSales", this.findSalesDuring30Day(json.getString("recipeName")));
            Collection<Stall> stalls = stallRepository.findAllByRecipesContains((Recipe) e);
            StringBuilder stringBuilder = new StringBuilder("");
            for (Stall r : stalls) {
                stringBuilder.append(r.getStallName());
                stringBuilder.append(" ");
            }
            json.put("stalls", stringBuilder.toString());
            array.add(json);
        }
        return array;
    }

    public long getTotalCount(String key) {
        long count = 0;
        switch (key) {
            case "Account":
                count = accountRepository.count();
                break;
            case "Material":
                count = materialRepository.count();
                break;
            case "MaterialOrder":
                count = materialOrderRepository.count();
                break;
            case "MaterialUsage":
                count = materialUsageRepository.count();
                break;
            case "OperationRecord":
                count = operationRecordRepository.count();
                break;
            case "Recipe":
                count = recipeRepository.count();
                break;
            case "ScheduleRecord":
                count = scheduleRecordRepository.count();
                break;
            case "Staff":
                count = staffRepository.count();
                break;
            case "Stall":
                count = stallRepository.count();
                break;
            case "Transaction":
                count = transactionRecordRepository.count();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
        return count;
    }

    public void removeByID(String id, String key) {
        switch (key) {
            case "Material":
                this.removeMaterial(id);
                break;
            case "MaterialOrder":
                materialOrderRepository.deleteById(Integer.parseInt(id));
                break;
            case "MaterialUsage":
                materialUsageRepository.deleteById(Integer.parseInt(id));
                break;
            case "OperationRecord":
                operationRecordRepository.deleteById(Integer.parseInt(id));
                break;
            case "Recipe":
                this.removeRecipe(id);
                break;
            case "ScheduleRecord":
                scheduleRecordRepository.deleteById(Integer.parseInt(id));
                break;
            case "Staff":
                this.removeStaff(Integer.parseInt(id));
                break;
            case "Stall":
                this.removeStall(id);
                break;
            case "Transaction":
                this.removeTransactionRecord(Integer.parseInt(id) );
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
    }

    public int getOwnID() {
        return account.getStaffID();
    }

    public String getOwnUsername() {
        return account.getAccountName();
    }

    public StaffCategoryTypes getOwnCategoryType() {
        return StaffCategoryTypes.getByIndex(account.getStaff().getStaffCategory());
    }

    public List<OperationRecord> getOwnOperationRecord() {
        return operationRecordRepository.findALLByStaffID(account.getStaffID());
    }

    public List<ScheduleRecord> getOwnScheduleRecord(boolean showFinished) {
        Timestamp time = (showFinished)?  new Timestamp(0) : new Timestamp(System.currentTimeMillis());
        return scheduleRecordRepository.findByStaffIDAndTimeScheduledToStartWorkingAfterOrderByTimeScheduledToStartWorkingDesc(account.getStaffID(), time);
    }

    public MaterialUsage getMaterialForStall(String materialName, String stallName, float amount) {
        Material material = materialRepository.findByName(materialName);
        Stall stall = stallRepository.findByStallName(stallName);
        if (material == null || stall == null) throw new RestrictedOperationException("no such material or stall!");

        Timestamp limit = getTimeLimit(material, 0);
        MaterialUsage materialUsage = null;
        List<MaterialOrder> materialOrders = materialOrderRepository.findAllUpToDate(materialName, limit);
        for (MaterialOrder e : materialOrders) {
            Float used = materialOrderRepository.getUsedAmount(e.getOperationStorageID());
            used = (used == null)? 0 : used;
            if (e.getMaterialAmount() - used - amount >= 0) {
                materialUsage = EntityFactor.getMaterialUsage(stall, material, e, amount);
                materialUsageRepository.saveAndFlush(materialUsage);
                break;
            } else {
                materialUsage = EntityFactor.getMaterialUsage(stall, material, e, e.getMaterialAmount() - used);
                materialUsageRepository.saveAndFlush(materialUsage);
                amount = amount - (e.getMaterialAmount() - used);
            }
        }
        return materialUsage;
    }
    /**
     * @author Zhining
     * @description
     * @param newPassword
     * @create 2020/5/12 8:59 上午
     **/
    public void changePassword(String newPassword){
        Account account = accountRepository.findByStaffID(this.account.getStaffID());
        account.setPasswordHashValue(newPassword);
        accountRepository.saveAndFlush(account);
    }
}
