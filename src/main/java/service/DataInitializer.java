package service;

import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class DataInitializer {
    private static Map<String, AccessInfo> accessInfoMap = new HashMap<>();
    private static Map<String, Staff> staffMap = new HashMap<>();
    private static Map<String, Account> accountMap = new HashMap<>();
    private static Map<String, Material> materialMap = new HashMap<>();
    private static Map<String, Recipe> recipeMap = new HashMap<>();
    private static Map<String, Stall> stallMap = new HashMap<>();
    private static Map<String,MaterialOrder> materialOrderMap = new HashMap<>();
    private static Map<Integer,ScheduleRecord> scheduleRecordMap = new HashMap<>();

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

    public static void run() {
        addAccessInfo();
        addStaff();
        addAccount();
        addMaterial();
        addRecipe();
        addStall();
        addMaterialOrder();
        addMaterialUsage();
        addTransactionRecord();
        addScheduleRecord();

        accessInfoMap = null;
        staffMap = null;
        accountMap = null;
        materialMap = null;
        recipeMap = null;
        stallMap = null;
        materialOrderMap = null;
        scheduleRecordMap = null;
    }

    private static void addAccessInfo() {
        accessInfoMap.put("none", EntityFactor.getAccessInfo("none", false, false, false));
        accessInfoMap.put("admin", EntityFactor.getAccessInfo("admin", true, true, true));
        accessInfoMap.put("clerk", EntityFactor.getAccessInfo("clerk", true, false, false));
        accessInfoMap.put("staffManager", EntityFactor.getAccessInfo("staffManager", false, true, false));
        accessInfoMap.put("stallManager", EntityFactor.getAccessInfo("stallManager", false, false, true));
        accessInfoRepository.saveAll(accessInfoMap.values());
        accessInfoRepository.flush();
    }

    private static void addStaff() {
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
        materialMap.put("rice", EntityFactor.getMaterial("rice", MaterialTypes.STAPLE, 1.0f, 5));
        materialMap.put("flower", EntityFactor.getMaterial("flower", MaterialTypes.STAPLE, 2.0f, 4));
        materialMap.put("noodle", EntityFactor.getMaterial("noodle", MaterialTypes.STAPLE, 3.0f, 3));
        materialMap.put("potato", EntityFactor.getMaterial("potato", MaterialTypes.STAPLE, 4.0f, 3));
        materialMap.put("fish", EntityFactor.getMaterial("fish", MaterialTypes.MEAT, 5.0f, 2));
        materialMap.put("pork", EntityFactor.getMaterial("pork", MaterialTypes.MEAT, 6.0f, 2));
        materialMap.put("beef", EntityFactor.getMaterial("beef", MaterialTypes.MEAT, 7.0f, 2));
        materialMap.put("chicken", EntityFactor.getMaterial("chicken", MaterialTypes.MEAT, 8.0f, 2));
        materialMap.put("sausage", EntityFactor.getMaterial("sausage", MaterialTypes.MEAT, 9.0f, 1));
        materialMap.put("duck_blood", EntityFactor.getMaterial("blood", MaterialTypes.MEAT, 10.0f, 1));
        materialMap.put("egg", EntityFactor.getMaterial("egg", MaterialTypes.MEAT, 10.0f, 1));
        materialMap.put("carrot", EntityFactor.getMaterial("carrot", MaterialTypes.VEGETABLE, 11.0f, 1));
        materialMap.put("onion", EntityFactor.getMaterial("onion", MaterialTypes.VEGETABLE, 12.0f, 3));
        materialMap.put("cabbage", EntityFactor.getMaterial("cabbage", MaterialTypes.VEGETABLE, 13.0f, 3));
        materialMap.put("cucumber", EntityFactor.getMaterial("cucumber", MaterialTypes.VEGETABLE, 14.0f, 5));
        materialMap.put("tomato", EntityFactor.getMaterial("tomato", MaterialTypes.VEGETABLE, 15.0f, 5));
        materialMap.put("millet", EntityFactor.getMaterial("millet", MaterialTypes.STAPLE, 0.5f, 5));
        materialMap.put("corn", EntityFactor.getMaterial("corn", MaterialTypes.VEGETABLE, 2.0f, 5));
        materialMap.put("lemon", EntityFactor.getMaterial("lemon", MaterialTypes.FRUIT, 7.0f, 5));
        materialMap.put("Pork_ribs", EntityFactor.getMaterial("Pork_ribs", MaterialTypes.MEAT, 50.0f, 5));
        materialMap.put("Pork_belly", EntityFactor.getMaterial("Pork_belly", MaterialTypes.MEAT, 45.0f, 5));
        materialMap.put("Sirloin", EntityFactor.getMaterial("Sirloin", MaterialTypes.MEAT, 70.0f, 5));//牛腩
        materialRepository.saveAll(materialMap.values());
        materialRepository.flush();
    }

    private static void addRecipe() {
        recipeMap.put("牛肉盖饭", EntityFactor.getRecipe("牛肉盖饭", 12.0f,
                materialMap.get("rice"), materialMap.get("pork")));
        recipeMap.put("兰州拉面", EntityFactor.getRecipe("兰州拉面", 8.0f,
                materialMap.get("noodle"), materialMap.get("pork")));
        recipeMap.put("鸡腿饭", EntityFactor.getRecipe("鸡腿饭", 15.0f,
                materialMap.get("rice"), materialMap.get("chicken")));
        recipeMap.put("麻辣烫", EntityFactor.getRecipe("麻辣烫", 20.0f,
                materialMap.get("potato"), materialMap.get("pork"), materialMap.get("beef"), materialMap.get("sausage"), materialMap.get("duck_blood")));
        recipeMap.put("蛋炒饭", EntityFactor.getRecipe("蛋炒饭", 5.0f,
                materialMap.get("rice"), materialMap.get("egg")));
        recipeMap.put("小笼包", EntityFactor.getRecipe("小笼包", 5.0f,
                materialMap.get("flower"), materialMap.get("beef"), materialMap.get("cabbage")));
        recipeMap.put("馄饨", EntityFactor.getRecipe("馄饨", 3.0f,
                materialMap.get("flower"), materialMap.get("beef")));
        recipeMap.put("土豆鸡块", EntityFactor.getRecipe("土豆鸡块", 7.0f,
                materialMap.get("potato"), materialMap.get("chicken"), materialMap.get("carrot")));
        recipeMap.put("狮子头", EntityFactor.getRecipe("狮子头", 7.0f,
                materialMap.get("pork"), materialMap.get("carrot")));
        recipeMap.put("鱼香肉丝",EntityFactor.getRecipe("鱼香肉丝",10.f,materialMap.get("carrot"),materialMap.get("pork"),materialMap.get("chicken")));
        recipeMap.put("牛肉酱",EntityFactor.getRecipe("牛肉酱",10.f,materialMap.get("beef")));
        recipeMap.put("腊肠饭",EntityFactor.getRecipe("腊肠饭",10.f,materialMap.get("beef"),materialMap.get("carrot"),materialMap.get("sausage"),materialMap.get("rice")));
        recipeMap.put("土豆烧牛腩",EntityFactor.getRecipe("土豆烧牛腩",12.5f,materialMap.get("potato"),materialMap.get("Sirloin")));
        recipeMap.put("鸡肉洋葱披萨",EntityFactor.getRecipe("鸡肉洋葱披萨",15.0f,
                materialMap.get("chicken"),materialMap.get("onion"),materialMap.get("flower")));
        recipeRepository.saveAll(recipeMap.values());
        recipeRepository.flush();
    }

    private static void addStall() {
        stallMap.put("自选美食", EntityFactor.getStallWithRecipes("自选美食", 1, 3000,
                recipeMap.get("土豆鸡块"), recipeMap.get("狮子头")));
        stallMap.put("广东风味", EntityFactor.getStallWithRecipes("广东风味", 2, 3000,
                recipeMap.get("鸡腿饭"), recipeMap.get("蛋炒饭"), recipeMap.get("牛肉盖饭")));
        stallMap.put("包子铺", EntityFactor.getStallWithRecipes("包子铺", 3, 3000,
                recipeMap.get("小笼包"), recipeMap.get("馄饨")));
        stallMap.put("兰州拉面", EntityFactor.getStallWithRecipes("兰州拉面", 4, 3000,
                recipeMap.get("兰州拉面")));
        stallMap.put("家常菜",EntityFactor.getStallWithRecipes("家常菜",5,3000,
                recipeMap.get("鱼香肉丝"),recipeMap.get("牛肉酱"),recipeMap.get("腊肠饭"),recipeMap.get("土豆烧牛腩")));
        stallMap.put("川菜", EntityFactor.getStallWithRecipes("川菜", 6, 3000,
                recipeMap.get("麻辣烫")));
        stallMap.put("披萨",EntityFactor.getStallWithRecipes("披萨",7,3000,
                recipeMap.get("鸡肉洋葱披萨")));
        stallRepository.saveAll(stallMap.values());
        stallRepository.flush();
    }

    private static void addMaterialUsage() {
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("自选美食"),
                materialMap.get("potato"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("potato0").getStorageRecord().getOperationID()),(float)(20*Math.random())));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("自选美食"),
                materialMap.get("chicken"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("chicken0").getStorageRecord().getOperationID()),(float)(20*Math.random())));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("自选美食"),
                materialMap.get("potato"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("potato1").getStorageRecord().getOperationID()),(float)(20*Math.random())));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("广东风味"),
                materialMap.get("egg"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("egg0").getStorageRecord().getOperationID()),(float)(20*Math.random())));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("家常菜"),
                materialMap.get("potato"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("potato0").getStorageRecord().getOperationID()),(float)(10*Math.random())));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("家常菜"),
                materialMap.get("Sirloin"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("Sirloin0").getStorageRecord().getOperationID()),(float)(5*Math.random())));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("兰州拉面"),
                materialMap.get("pork"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("pork0").getStorageRecord().getOperationID()),(float)(5)));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("兰州拉面"),
                materialMap.get("noodle"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("noodle0").getStorageRecord().getOperationID()),(float)(7)));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("兰州拉面"),
                materialMap.get("pork"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("pork0").getStorageRecord().getOperationID()),(float)(5*2)));
        materialUsageRepository.save(EntityFactor.getMaterialUsage(stallMap.get("兰州拉面"),
                materialMap.get("noodle"),materialOrderRepository.findByOperationStorageID(materialOrderMap.get("noodle0").getStorageRecord().getOperationID()),(float)(7*2)));

    }

    private static void addTransactionRecord() {
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(1,
                recipeMap.get("小笼包").getPrice(),
                recipeMap.get("小笼包"),
                stallMap.get("包子铺")));
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(1,
                recipeMap.get("小笼包").getPrice(),
                recipeMap.get("小笼包"),
                stallMap.get("包子铺")));
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(2,
                recipeMap.get("土豆鸡块").getPrice(),
                recipeMap.get("土豆鸡块"),
                stallMap.get("自选美食")));
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(1,
                recipeMap.get("麻辣烫").getPrice(),
                recipeMap.get("麻辣烫"),
                stallMap.get("川菜")));
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(1,
                recipeMap.get("土豆烧牛腩").getPrice(),
                recipeMap.get("土豆烧牛腩"),
                stallMap.get("家常菜")));
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(1,
                recipeMap.get("兰州拉面").getPrice(),
                recipeMap.get("兰州拉面"),
                stallMap.get("兰州拉面")));
        transactionRecordRepository.saveAndFlush(EntityFactor.getTransactionRecord(1,
                recipeMap.get("兰州拉面").getPrice(),
                recipeMap.get("兰州拉面"),
                stallMap.get("兰州拉面")));
    }

    private static void addOperationRecord() {

    }

    private static void addMaterialOrder() {
        materialOrderMap.put("potato0",EntityFactor.getMaterialOrder(staffMap.get("admin"),"potato0",
                materialMap.get("potato"),(float)(100*Math.random())));
        materialOrderMap.put("rice0",EntityFactor.getMaterialOrder(staffMap.get("admin"),"rice0",
                materialMap.get("rice"),(float)(200*Math.random())));
        materialOrderMap.put("chicken0",EntityFactor.getMaterialOrder(staffMap.get("storeroomClerk1"),"chicken0",
                materialMap.get("chicken"),(float)(300*Math.random())));
        materialOrderMap.put("egg0",EntityFactor.getMaterialOrder(staffMap.get("storeroomClerk1"),"egg0",
                materialMap.get("egg"),(float)(450*Math.random())));
        materialOrderMap.put("potato1",EntityFactor.getMaterialOrder(staffMap.get("admin"),"potato1",
                materialMap.get("potato"),(float)(100*Math.random())));
        materialOrderMap.put("pork0",EntityFactor.getMaterialOrder(staffMap.get("admin"),"pork0",
                materialMap.get("pork"),(float)(200*Math.random())));

        materialOrderMap.put("egg1",EntityFactor.getMaterialOrder(staffMap.get("admin"),"egg1",
                materialMap.get("egg"),(float)(100*Math.random())));
        materialOrderMap.put("noodle0",EntityFactor.getMaterialOrder(staffMap.get("admin"),"noodle0",
                materialMap.get("noodle"),(float)(200*Math.random())));
        materialOrderMap.put("sausage0",EntityFactor.getMaterialOrder(staffMap.get("storeroomClerk1"),"sausage0",
                materialMap.get("sausage"),(float)(300*Math.random())));
        materialOrderMap.put("onion0",EntityFactor.getMaterialOrder(staffMap.get("storeroomClerk1"),"onion0",
                materialMap.get("onion"),(float)(450*Math.random())));
        materialOrderMap.put("Sirloin0",EntityFactor.getMaterialOrder(staffMap.get("admin"),"Sirloin0",
                materialMap.get("Sirloin"),(float)(100*Math.random())));


        EntityFactor.confirmMaterialOrder(staffMap.get("admin"), "C_potato0",materialOrderMap.get("potato0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("admin"), "C_rice0",materialOrderMap.get("rice0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_chicken0",materialOrderMap.get("chicken0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_egg0",materialOrderMap.get("egg0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("admin"), "C_potato1",materialOrderMap.get("potato1"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_egg1",materialOrderMap.get("egg1"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_noodle0",materialOrderMap.get("noodle0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_sausage0",materialOrderMap.get("sausage0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_onion0",materialOrderMap.get("onion0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_Sirloin0",materialOrderMap.get("Sirloin0"));
        EntityFactor.confirmMaterialOrder(staffMap.get("storeroomClerk1"), "C_pork0",materialOrderMap.get("pork0"));
        materialOrderRepository.saveAll(materialOrderMap.values());
        materialOrderRepository.flush();
    }

    private static void addScheduleRecord() {
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 08:00:00"),
                Timestamp.valueOf("2020-03-12 13:00:00"),
                staffMap.get("storeroomClerk4"),staffMap.get("manager1"),"checkListOfPotato"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-13 09:00:00"),
                Timestamp.valueOf("2020-03-13 15:00:00"),
                staffMap.get("storeroomClerk2"),staffMap.get("manager1"),"assign food to stall"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-14 13:00:00"),
                Timestamp.valueOf("2020-03-14 15:00:00"),
                staffMap.get("storeroomClerk2"),staffMap.get("manager1"),"assign food to stall"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 07:00:00"),
                Timestamp.valueOf("2020-03-12 08:30:00"),
                staffMap.get("cleaner2"),staffMap.get("manager1"),"clean floor 2"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 12:30:00"),
                Timestamp.valueOf("2020-03-12 13:00:00"),
                staffMap.get("cleaner1"),staffMap.get("manager1"),"clean floor 1"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 11:00:00"),
                Timestamp.valueOf("2020-03-12 12:30:00"),
                staffMap.get("storeroomClerk3"),staffMap.get("manager1"),"checkListOfPotato"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 08:00:00"),
                Timestamp.valueOf("2020-03-12 09:00:00"),
                staffMap.get("storeroomClerk3"),staffMap.get("manager1"),"buySomeThing"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 17:00:00"),
                Timestamp.valueOf("2020-03-12 18:00:00"),
                staffMap.get("cleaner1"),staffMap.get("manager1"),"Meeting"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 17:00:00"),
                Timestamp.valueOf("2020-03-12 18:00:00"),
                staffMap.get("cleaner2"),staffMap.get("manager1"),"Meeting"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 17:00:00"),
                Timestamp.valueOf("2020-03-12 18:00:00"),
                staffMap.get("cleaner3"),staffMap.get("manager1"),"Meeting"));
        scheduleRecordRepository.saveAndFlush(EntityFactor.getScheduleRecord(
                Timestamp.valueOf("2020-03-12 17:00:00"),
                Timestamp.valueOf("2020-03-12 18:00:00"),
                staffMap.get("cleaner4"),staffMap.get("manager1"),"Meeting"));
        scheduleRecordRepository.saveAll(scheduleRecordMap.values());
        scheduleRecordRepository.flush();
    }

    public static void clear() {
        scheduleRecordRepository.deleteAll();
        transactionRecordRepository.deleteAll();
        materialOrderRepository.deleteAll();
        operationRecordRepository.deleteAll();
        materialUsageRepository.deleteAll();
        accountRepository.deleteAll();
        staffRepository.deleteAll();
        accessInfoRepository.deleteAll();
        stallRepository.deleteAll();
        recipeRepository.deleteAll();
        materialRepository.deleteAll();
    }
}
