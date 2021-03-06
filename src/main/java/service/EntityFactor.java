package service;

import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.enums.OperationType;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

/**
 * provide useful methods to generate table entities
 */
@Service
public abstract class EntityFactor {
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
    /**
     * @see AccessInfo
     *
     * generate AccessInfo entity
     * @param position position name
     * @param accessToMaterial permission to material
     * @param accessToStaff permission to staff
     * @param accessToStall permission to stall
     * @return AccessInfo entity
     */
    protected static AccessInfo getAccessInfo(String position, boolean accessToMaterial, boolean accessToStaff, boolean accessToStall) {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setPosition(position);
        accessInfo.setAccessToMaterial(accessToMaterial);
        accessInfo.setAccessToStaff(accessToStaff);
        accessInfo.setAccessToStall(accessToStall);
        return accessInfo;
    }

    /**
     * @see Account
     * @see Staff
     * @see AccessInfo
     *
     * generate Account entity
     * @param staff account owner
     * @param accessInfo access level of this account
     * @param accountName username of account
     * @param passwordHashValue password of account
     * @return Account entity
     */
    protected static Account getAccount(Staff staff, AccessInfo accessInfo, String accountName, String passwordHashValue) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setPasswordHashValue(passwordHashValue);
        account.setStaffID(staff.getStaffID());
        account.setStaff(staff);
        account.setAccessInfo(accessInfo);
        return account;
    }

    /**
     * @see Material
     * @see MaterialTypes
     *
     * generate Material entity
     * @param name material name
     * @param types material type
     * @param unitPrice price per unit
     * @param availablePeriod day before unavaliable
     * @return Material entity
     */
    protected static Material getMaterial(String name, MaterialTypes types, float unitPrice, float availablePeriod) {
        Material material = new Material();
        material.setName(name);
        material.setType((byte) types.ordinal());
        material.setUnitPrice(unitPrice);
        material.setAvailablePeriod(availablePeriod);
        return material;
    }

    /**
     * @see MaterialOrder
     * @see OperationRecord
     * @see Staff
     * @see Material
     *
     * generate MaterialOrder entiry
     * @param staff staff who make the order
     * @param note comments
     * @param material material been ordered
     * @param materialAmount material amount been ordered
     * @return MaterialOrder entiry
     */
    protected static MaterialOrder getMaterialOrder(Staff staff, String note, Material material, float materialAmount) {
        OperationRecord operationRecord = getOperationRecord(OperationType.ORDER, staff, false, note);
        OperationRecordRepository operationRecordRepository = (OperationRecordRepository) DAO_Type.OPERATION_RECORD.getTableRepository();
        operationRecordRepository.saveAndFlush(operationRecord);

        MaterialOrder materialOrder = new MaterialOrder();
        materialOrder.setOperationOrderID(operationRecord.getOperationID());
        materialOrder.setMaterial(material);
        materialOrder.setMaterialAmount(materialAmount);
        materialOrder.setOrderRecord(operationRecord);
        operationRecord.setOrderedMaterialRecord(materialOrder);
        return materialOrder;
    }

    /**
     * @see MaterialOrder
     * @see OperationRecord
     * @see Staff
     *
     * comfirm the material order after confirmed
     * @param staff staff who confirm the order
     * @param note comments
     * @param materialOrder the order record been confirmed
     * @return the order record after confirmed
     */
    protected static MaterialOrder confirmMaterialOrder(Staff staff, String note, MaterialOrder materialOrder) {
        OperationRecord operationRecord = getOperationRecord(OperationType.PULL, staff, false, note);
        OperationRecordRepository operationRecordRepository = (OperationRecordRepository) DAO_Type.OPERATION_RECORD.getTableRepository();
        operationRecordRepository.saveAndFlush(operationRecord);

        materialOrder.setStorageRecord(operationRecord);
        return materialOrder;
    }

    /**
     * @see OperationRecord
     * @see Staff
     * @see OperationType
     *
     * generate OperationRecord entity
     * @param type operation type
     * @param staff staff who did the operation
     * @param sendMessage weather a message will be sent to others
     * @param note comments
     * @return OperationRecord entity
     */
    protected static OperationRecord getOperationRecord(OperationType type, Staff staff, boolean sendMessage, String note) {
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setOperationType((byte) type.ordinal());
        operationRecord.setStaff(staff);
        operationRecord.setWillSendUpdateMessage(sendMessage);
        operationRecord.setNote(note);
        return operationRecord;
    }

    /**
     * @see Recipe
     * @see Material
     *
     * generate Recipe entity
     * @param recipeName recipe name
     * @param price recipe price
     * @param materials materials that recipe needs
     * @return Recipe entity
     */
    protected static Recipe getRecipe(String recipeName, float price, Collection<Material> materials) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeName);
        recipe.setPrice(price);
        recipe.getMaterials().addAll(materials);
        return recipe;
    }

    protected static Recipe getRecipe(String recipeName, float price, Material... materials) {
        return getRecipe(recipeName, price, Arrays.asList(materials));
    }

    /**
     * @see ScheduleRecord
     * @see Staff
     * @see OperationRecord
     *
     * generate ScheduleRecord entity
     * @param start time start to work
     * @param end time end working
     * @param targetStaff staff been scheduled
     * @param manager staff who made this schedule record
     * @param note comments
     * @return ScheduleRecord entity
     */
    protected static ScheduleRecord getScheduleRecord(Timestamp start, Timestamp end, Staff targetStaff, Staff manager, String note) {
        OperationRecord operationRecord = getOperationRecord(OperationType.DAY_SHIFT, manager, true, note);
        OperationRecordRepository operationRecordRepository = (OperationRecordRepository) DAO_Type.OPERATION_RECORD.getTableRepository();
        operationRecordRepository.saveAndFlush(operationRecord);

        ScheduleRecord scheduleRecord = new ScheduleRecord();
        scheduleRecord.setTimeScheduledToStartWorking(start);
        scheduleRecord.setTimeScheduledToEndWorking(end);
        scheduleRecord.setStaff(targetStaff);
        scheduleRecord.setOperationRecord(operationRecord);
        scheduleRecord.setOperationID(operationRecord.getOperationID());
//        scheduleRecord.setStaffID(manager.getStaffID());
        operationRecord.setScheduleRecord(scheduleRecord);
        return scheduleRecord;
    }

    /**
     * @see Staff
     *
     * generate Staff entity
     * @param staffName name of the staff
     * @return Staff entity
     */
    protected static Staff getStaff(String staffName) {
        Staff staff = new Staff();
        staff.setStaffName(staffName);
        return staff;
    }

    /**
     * @see Staff
     *
     * generate Staff entity
     * @param staffName name of the staff
     * @param types type of the staff category
     * @return Staff entity
     */
    protected static Staff getStaff(String staffName, StaffCategoryTypes types) {
        Staff staff = getStaff(staffName);
        staff.setStaffCategory((byte) types.ordinal());
        return staff;
    }

    /**
     * @see Staff
     *
     * generate Staff entity
     * @param staffName name of the staff
     * @param types type of the staff category
     * @param start time start to work
     * @param end time end to work
     * @return Staff entity
     */
    protected static Staff getStaff(String staffName, StaffCategoryTypes types, Time start, Time end) {
        Staff staff = getStaff(staffName, types);
        staff.setTimeStartWorking(start);
        staff.setTimeEndWorking(end);
        return staff;
    }

    /**
     * @see Stall
     *
     * generate Stall entity
     * @param stallName stall name
     * @param stallLocation stall location
     * @param stallRent stall rent per month
     * @return Stall entity
     */
    protected static Stall getStall(String stallName, int stallLocation, float stallRent) {
        Stall stall = new Stall();
        stall.setStallName(stallName);
        stall.setStallLocation(stallLocation);
        stall.setStallRent(stallRent);
        return stall;
    }

    /**
     * @see Stall
     * @see Recipe
     *
     * generate Stall entity containing it's available recipe
     * @param stallName stall name
     * @param stallLocation stall location
     * @param stallRent stall rent
     * @param recipes available recipe
     * @return Stall entity
     */
    protected static Stall getStallWithRecipes(String stallName, int stallLocation, float stallRent, Collection<Recipe> recipes) {
        Stall stall = getStall(stallName, stallLocation, stallRent);
        stall.getRecipes().addAll(recipes);
        return stall;
    }

    protected static Stall getStallWithRecipes(String stallName, int stallLocation, float stallRent, Recipe... recipes) {
        return getStallWithRecipes(stallName, stallLocation, stallRent, Arrays.asList(recipes));
    }

    /**
     * @see TransactionRecord
     * @see Recipe
     * @see Stall
     *
     * generate TransactionRecord entity
     * @param amount amount of recipe been sold
     * @param price total price of the transaction
     * @param recipe recipe type been sold
     * @param stall stall that make this record
     * @return TransactionRecord entity
     */
    protected static TransactionRecord getTransactionRecord(int amount, float price, Recipe recipe, Stall stall) {
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setNumbers(amount);
        transactionRecord.setTransactionPrice(price);
        transactionRecord.setRecipe(recipe);
        transactionRecord.setStall(stall);
        return transactionRecord;
    }

    /**
     * generate MaterialUsage entity
     * @param stall stall that uses the material
     * @param material the material been used
     * @param amount the amount been used
     * @return MaterialUsage entity
     */
    protected static MaterialUsage getMaterialUsage(Stall stall, Material material, MaterialOrder materialOrder, float amount) {
        MaterialUsage materialUsage = new MaterialUsage();
        materialUsage.setAmount(amount);
        materialUsage.setStall(stall);
        materialUsage.setMaterial(material);
        materialUsage.setMaterialOrder(materialOrder);
        return materialUsage;
    }
}
