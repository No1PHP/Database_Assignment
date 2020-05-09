package service;

import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.EntityFactor;
import dao.enums.MaterialTypes;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public abstract class DataAdder {
    private static final Map<String, AccessInfo> accessInfoMap = new HashMap<>();
    private static final Map<String, Staff> staffMap = new HashMap<>();
    private static final Map<String, Account> accountMap = new HashMap<>();
    private static final Map<String, Material> materialMap = new HashMap<>();
    private static final Map<String, Recipe> recipeMap = new HashMap<>();

    public static void run() {
        addAccessInfo();
        addStaff();
        addAccount();
        addMaterial();
        addRecipe();
    }

    private static void addAccessInfo() {
        AccessInfoRepository accessInfoRepository = (AccessInfoRepository) DAO_Type.ACCESS_INFO.getTableRepository();
        accessInfoMap.put("none", EntityFactor.getAccessInfo("none", false, false, false));
        accessInfoMap.put("admin", EntityFactor.getAccessInfo("admin", true, true, true));
        accessInfoMap.put("clerk", EntityFactor.getAccessInfo("clerk", true, false, false));
        accessInfoMap.put("staffManager", EntityFactor.getAccessInfo("staffManager", false, true, false));
        accessInfoMap.put("stallManager", EntityFactor.getAccessInfo("stallManager", false, false, true));
        accessInfoRepository.saveAll(accessInfoMap.values());
        accessInfoRepository.flush();
    }

    private static void addStaff() {
        StaffRepository staffRepository = (StaffRepository) DAO_Type.STAFF.getTableRepository();
        Time time0 = new Time(0,0,0);
        Time time6 = new Time(6,0,0);
        Time time8 = new Time(8,0,0);
        Time time18 = new Time(18,0,0);
        staffMap.put("none", EntityFactor.getStaff("none", StaffCategoryTypes.NONE, time0, time0));
        staffMap.put("admin", EntityFactor.getStaff("admin", StaffCategoryTypes.ADMIN, time8, time18));
        staffMap.put("manager1", EntityFactor.getStaff("manager1", StaffCategoryTypes.MANAGER, time8, time18));
        staffMap.put("manager2", EntityFactor.getStaff("manager2", StaffCategoryTypes.MANAGER, time8, time18));
        staffMap.put("storeroomClerk1", EntityFactor.getStaff("storeroomClerk1", StaffCategoryTypes.STOREROOM_CLERK, time8, time18));
        staffMap.put("storeroomClerk2", EntityFactor.getStaff("storeroomClerk2", StaffCategoryTypes.STOREROOM_CLERK, time8, time18));
        staffMap.put("storeroomClerk3", EntityFactor.getStaff("storeroomClerk3", StaffCategoryTypes.STOREROOM_CLERK, time8, time18));
        staffMap.put("storeroomClerk4", EntityFactor.getStaff("storeroomClerk4", StaffCategoryTypes.STOREROOM_CLERK, time8, time18));
        staffMap.put("cleaner1", EntityFactor.getStaff("cleaner1", StaffCategoryTypes.CLEANER, time6, time18));
        staffMap.put("cleaner2", EntityFactor.getStaff("cleaner2", StaffCategoryTypes.CLEANER, time6, time18));
        staffMap.put("cleaner3", EntityFactor.getStaff("cleaner3", StaffCategoryTypes.CLEANER, time6, time18));
        staffMap.put("cleaner4", EntityFactor.getStaff("cleaner4", StaffCategoryTypes.CLEANER, time6, time18));
        staffMap.put("cleaner5", EntityFactor.getStaff("cleaner5", StaffCategoryTypes.CLEANER, time6, time18));
        staffRepository.saveAll(staffMap.values());
        staffRepository.flush();
    }

    private static void addAccount() {
        AccountRepository accountRepository = (AccountRepository) DAO_Type.ACCOUNT.getTableRepository();
        accountMap.put("none", EntityFactor.getAccount(staffMap.get("none"), accessInfoMap.get("none"), "none", "123456"));
        accountMap.put("admin", EntityFactor.getAccount(staffMap.get("admin"), accessInfoMap.get("admin"), "admin", "admin"));
        accountMap.put("manager1", EntityFactor.getAccount(staffMap.get("manager1"), accessInfoMap.get("staffManager"), "manager1", "aaa"));
        accountMap.put("manager2", EntityFactor.getAccount(staffMap.get("manager2"), accessInfoMap.get("stallManager"), "manager2", "bbb"));
        accountMap.put("storeroomClerk1", EntityFactor.getAccount(staffMap.get("storeroomClerk1"), accessInfoMap.get("clerk"), "storeroomClerk1", "ccc"));
        accountMap.put("storeroomClerk2", EntityFactor.getAccount(staffMap.get("storeroomClerk2"), accessInfoMap.get("clerk"), "storeroomClerk2", "ddd"));
        accountMap.put("storeroomClerk3", EntityFactor.getAccount(staffMap.get("storeroomClerk3"), accessInfoMap.get("clerk"), "storeroomClerk3", "eee"));
        accountMap.put("storeroomClerk4", EntityFactor.getAccount(staffMap.get("storeroomClerk4"), accessInfoMap.get("clerk"), "storeroomClerk4", "fff"));
        accountMap.put("cleaner1", EntityFactor.getAccount(staffMap.get("cleaner1"), accessInfoMap.get("none"), "cleaner1", "ggg"));
        accountMap.put("cleaner2", EntityFactor.getAccount(staffMap.get("cleaner2"), accessInfoMap.get("none"), "cleaner2", "hhh"));
        accountMap.put("cleaner3", EntityFactor.getAccount(staffMap.get("cleaner3"), accessInfoMap.get("none"), "cleaner3", "iii"));
        accountMap.put("cleaner4", EntityFactor.getAccount(staffMap.get("cleaner4"), accessInfoMap.get("none"), "cleaner4", "jjj"));
        accountMap.put("cleaner5", EntityFactor.getAccount(staffMap.get("cleaner5"), accessInfoMap.get("none"), "cleaner5", "kkk"));
        accountRepository.saveAll(accountMap.values());
        accountRepository.flush();
    }

    private static void addMaterial() {
        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        materialMap.put("rice", EntityFactor.getMaterial("rice", MaterialTypes.STAPLE, 1.0f, 5));
        materialMap.put("flower", EntityFactor.getMaterial("flower", MaterialTypes.STAPLE, 2.0f, 4));
        materialMap.put("noodle", EntityFactor.getMaterial("noodle", MaterialTypes.STAPLE, 3.0f, 3));
        materialMap.put("potato", EntityFactor.getMaterial("potato", MaterialTypes.STAPLE, 4.0f, 3));
        materialMap.put("fish", EntityFactor.getMaterial("fish", MaterialTypes.MEAT, 5.0f, 2));
        materialMap.put("pork", EntityFactor.getMaterial("pork", MaterialTypes.MEAT, 6.0f, 2));
        materialMap.put("beef", EntityFactor.getMaterial("beef", MaterialTypes.MEAT, 7.0f, 2));
        materialMap.put("chicken", EntityFactor.getMaterial("chicken", MaterialTypes.MEAT, 8.0f, 2));
        materialMap.put("sausage", EntityFactor.getMaterial("sausage", MaterialTypes.MEAT, 9.0f, 1));
        materialMap.put("pig blood", EntityFactor.getMaterial("blood", MaterialTypes.MEAT, 10.0f, 1));
        materialMap.put("egg", EntityFactor.getMaterial("egg", MaterialTypes.MEAT, 10.0f, 1));
        materialMap.put("carrot", EntityFactor.getMaterial("carrot", MaterialTypes.VEGETABLE, 11.0f, 1));
        materialMap.put("onion", EntityFactor.getMaterial("onion", MaterialTypes.VEGETABLE, 12.0f, 3));
        materialMap.put("cabbage", EntityFactor.getMaterial("cabbage", MaterialTypes.VEGETABLE, 13.0f, 3));
        materialMap.put("cucumber", EntityFactor.getMaterial("cucumber", MaterialTypes.VEGETABLE, 14.0f, 5));
        materialMap.put("tomato", EntityFactor.getMaterial("tomato", MaterialTypes.VEGETABLE, 15.0f, 5));
        materialRepository.saveAll(materialMap.values());
        materialRepository.flush();
    }

    private static void addRecipe() {
        RecipeRepository recipeRepository = (RecipeRepository) DAO_Type.RECIPE.getTableRepository();
        recipeMap.put("牛肉盖饭", EntityFactor.getRecipe("牛肉盖饭", 12.0f,
                materialMap.get("rice"), materialMap.get("pork")));
        recipeMap.put("兰州拉面", EntityFactor.getRecipe("兰州拉面", 8.0f,
                materialMap.get("noodle"), materialMap.get("pork")));
        recipeMap.put("鸡腿饭", EntityFactor.getRecipe("鸡腿饭", 15.0f,
                materialMap.get("rice"), materialMap.get("chicken")));
        recipeMap.put("麻辣烫", EntityFactor.getRecipe("麻辣烫", 20.0f,
                materialMap.get("potato"), materialMap.get("pork"), materialMap.get("beef"), materialMap.get("sausage"), materialMap.get("pig blood")));
        recipeMap.put("蛋炒饭", EntityFactor.getRecipe("蛋炒饭", 5.0f,
                materialMap.get("rice"), materialMap.get("egg")));
        recipeRepository.saveAll(recipeMap.values());
        recipeRepository.flush();
    }

    private static void addStall() {

    }

    private static void addMaterialUsage() {

    }

    private static void addMaterialOrder() {

    }

    private static void addOperationRecord() {

    }

    private static void addScheduleRecord() {

    }

    private static void addTransactionRecord() {

    }
}
