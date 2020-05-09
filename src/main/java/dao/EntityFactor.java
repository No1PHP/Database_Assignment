package dao;

import dao.enums.MaterialTypes;
import dao.enums.OperationType;
import dao.tables.*;

import java.sql.Date;
import java.util.Collection;

/**
 * provide useful methods to generate table entities
 */
public abstract class EntityFactor {
    /**
     * @see AccessInfo
     *
     * generate AccessInfo entity
     * @param position position name
     * @param accessToOrder permission to order
     * @param accessToStaff permission to staff
     * @param accessToStock permission to stock
     * @return AccessInfo entity
     */
    public static AccessInfo getAccessInfo(String position, boolean accessToOrder, boolean accessToStaff, boolean accessToStock) {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setPosition(position);
        accessInfo.setAccessToOrder(accessToOrder);
        accessInfo.setAccessToStaff(accessToStaff);
        accessInfo.setAccessToStock(accessToStock);
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
    public static Account getAccount(Staff staff, AccessInfo accessInfo, String accountName, String passwordHashValue) {
        Account account = new Account();
        account.setAccountName(accountName);
        account.setPasswordHashValue(passwordHashValue);
        account.setStaff(staff);
        staff.setAccount(account);
        account.setAccessInfo(accessInfo);
        accessInfo.getAccounts().add(account);
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
    public static Material getMaterial(String name, MaterialTypes types, float unitPrice, int availablePeriod) {
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
    public static MaterialOrder getMaterialOrder(Staff staff, String note, Material material, float materialAmount) {
        OperationRecord operationRecord = getOperationRecord(OperationType.ORDER, staff, false, note);
        MaterialOrder materialOrder = new MaterialOrder();
        materialOrder.setMaterial(material);
        material.getMaterialOrders().add(materialOrder);
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
    public static MaterialOrder confirmMaterialOrder(Staff staff, String note, MaterialOrder materialOrder) {
        OperationRecord operationRecord = getOperationRecord(OperationType.PULL, staff, false, note);
        materialOrder.setStorageRecord(operationRecord);
        operationRecord.setStorageMaterialRecord(materialOrder);
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
    public static OperationRecord getOperationRecord(OperationType type, Staff staff, boolean sendMessage, String note) {
        OperationRecord operationRecord = new OperationRecord();
        operationRecord.setOperationType((byte) type.ordinal());
        operationRecord.setStaff(staff);
        operationRecord.setWillSendUpdateMessage(sendMessage);
        operationRecord.setNote(note);
        staff.getOperationRecords().add(operationRecord);
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
    public static Recipe getRecipe(String recipeName, float price, Collection<Material> materials) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeName);
        recipe.setPrice(price);
        recipe.getMaterials().addAll(materials);
        for (Material e : materials) {
            e.getRecipes().add(recipe);
        }
        return recipe;
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
    public static ScheduleRecord getScheduleRecord(Date start, Date end, Staff targetStaff, Staff manager, String note) {
        OperationRecord operationRecord = getOperationRecord(OperationType.DAY_SHIFT, manager, true, note);
        ScheduleRecord scheduleRecord = new ScheduleRecord();
        scheduleRecord.setTimeScheduledToStartWorking(start);
        scheduleRecord.setTimeScheduledToEndWorking(end);
        scheduleRecord.setStaff(targetStaff);
        targetStaff.getScheduleRecords().add(scheduleRecord);
        scheduleRecord.setOperationRecord(operationRecord);
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
    public static Staff getStaff(String staffName) {
        Staff staff = new Staff();
        staff.setStaffName(staffName);
        return staff;
    }

    /**
     * @see Staff
     * @see Account
     * @see AccessInfo
     *
     * generate Staff entity with corresponding account
     * @param staffName staff name
     * @param accessInfo account level
     * @param accountName account username
     * @param passwordHashValue account password
     * @return Staff entity
     */
    public static Staff getStaffWithAccount(String staffName, AccessInfo accessInfo, String accountName, String passwordHashValue) {
        Staff staff = getStaff(staffName);
        getAccount(staff, accessInfo, accountName, passwordHashValue);
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
    public static Stall getStall(String stallName, int stallLocation, float stallRent) {
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
    public static Stall getStallWithRecipes(String stallName, int stallLocation, float stallRent, Collection<Recipe> recipes) {
        Stall stall = getStall(stallName, stallLocation, stallRent);
        stall.getRecipes().addAll(recipes);
        for (Recipe e : recipes) {
            e.getStalls().add(stall);
        }
        return stall;
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
    public static TransactionRecord getTransactionRecord(int amount, float price, Recipe recipe, Stall stall) {
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setNumbers(amount);
        transactionRecord.setTransactionPrice(price);
        transactionRecord.setRecipe(recipe);
        recipe.getTransactionRecords().add(transactionRecord);
        transactionRecord.setStall(stall);
        stall.getTransactionRecords().add(transactionRecord);
        return transactionRecord;
    }
}