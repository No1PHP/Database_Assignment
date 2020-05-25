import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.enums.StaffCategoryTypes;
import dao.tables.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DataInitializer;
import service.EntityFactor;
import service.Service;
import service.exceptions.IllegalRequestException;
import service.exceptions.RestrictedOperationException;

import javax.transaction.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ServiceTest {
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
    public Service service;


    @Before
    public void setUp() throws Exception {
        DataInitializer.clear();
        DataInitializer.run();
        service = Service.connect("admin", "admin");
        System.out.println("finish init");
    }

    @Test
    public void saveMaterial() {
        service.saveMaterial("testMaterial1", MaterialTypes.MEAT, 1.1f, 4.2f);
        assertNotNull(materialRepository.findByName("testMaterial1"));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void removeMaterial() {
        service.removeMaterial("corn");
        assertNull(materialRepository.findByName("corn"));
    }

    @Test
    public void saveStall() {
        List<Stall> stallList = stallRepository.findAll(Sort.by("stallLocation").descending());
        service.saveStall("testStall", stallList.get(0).getStallLocation()+1, 1000);
        assertNotNull(stallRepository.findByStallName("testStall"));
    }

    @Test
    public void saveStallWithRecipes() {
        List<Stall> stallList = stallRepository.findAll(Sort.by("stallLocation").descending());
        List<Recipe> recipes = new LinkedList<>();
        recipes.add(recipeRepository.findByRecipeName("小笼包"));
        recipes.add(recipeRepository.findByRecipeName("馄饨"));
        recipes.add(recipeRepository.findByRecipeName("蛋炒饭"));

        service.saveStallWithRecipes("testStall", stallList.get(0).getStallLocation()+1, 1000, recipes);
        Stall stall = stallRepository.findByStallName("testStall");
        assertNotNull(stall);
        assertEquals(stall.getRecipes().size(), recipes.size());
    }

    @Test
    public void removeStall() {
        service.removeStall("披萨");
        assertNull(stallRepository.findByStallName("披萨"));
    }

    @Test
    public void insertStaff() {
        Staff staff = service.insertStaff("testStaff", StaffCategoryTypes.CLEANER, Time.valueOf("07:00:00"), Time.valueOf("15:00:00"));
        assertNotNull(staffRepository.findByStaffID(staff.getStaffID()));
    }

    @Test
    public void updateStaff() {
        Staff staff = service.insertStaff("testStaff", StaffCategoryTypes.CLEANER, Time.valueOf("07:00:00"), Time.valueOf("15:00:00"));
        service.updateStaff(staff.getStaffID(), StaffCategoryTypes.STOREROOM_CLERK, Time.valueOf("07:00:00"), Time.valueOf("15:00:00"));
        assertEquals((long)staffRepository.findByStaffID(staff.getStaffID()).getStaffCategory(), StaffCategoryTypes.STOREROOM_CLERK.ordinal());
    }

    @Test
    public void removeStaff() {
        service.removeStaff(staffRepository.findALLByStaffName("storeroomClerk4").get(0).getStaffID());
        assertEquals(0, staffRepository.findALLByStaffName("storeroomClerk4").size());
    }

    @Test
    public void saveAccount() {
        Staff staff = staffRepository.findALLByStaffName("cleaner1").get(0);
        service.saveAccount(staff.getStaffID(), "clerk", "cleaner1test", "passwordTest");
        Account account = staffRepository.findByStaffID(staff.getStaffID()).getAccount();
        assertEquals("clerk", account.getPosition());
        assertEquals("cleaner1test", account.getAccountName());
        assertEquals("passwordTest", account.getPasswordHashValue());
        service.saveAccount(staff.getStaffID(), "clerk", "cleaner1test", "newPassword");
        account = staffRepository.findByStaffID(staff.getStaffID()).getAccount();
        assertEquals("clerk", account.getPosition());
        assertEquals("cleaner1test", account.getAccountName());
        assertEquals("newPassword", account.getPasswordHashValue());
    }

    @Test
    public void saveAccessInfo() {
        service.saveAccessInfo("testPosition", true, true, false);
        AccessInfo accessInfo = accessInfoRepository.findByPosition("testPosition");
        assertNotNull(accessInfo);
        assertEquals(true, accessInfo.getAccessToMaterial());
        assertEquals(true, accessInfo.getAccessToStaff());
        assertEquals(false, accessInfo.getAccessToStall());
    }

    @Test
    public void removeAccessInfo() {
        service.saveAccessInfo("testPosition", true, true, false);
        service.removeAccessInfo("testPosition");
        assertNull(accessInfoRepository.findByPosition("testPosition"));
    }

    @Test
    public void getMaterialAvailableAmount() {
        assertTrue(service.getMaterialAvailableAmount("egg") >= 0);
    }

    @Test
    public void getALLMaterialUsageBetween() {
        Map<String, Float> map = service.getALLMaterialUsageBetween(Timestamp.valueOf("2000-1-1 12:00:00"), new Timestamp(System.currentTimeMillis()));
        assertEquals(22, map.size());
    }

    @Test
    public void getMaterialUsagePrediction() {
        Map<String, Float> map = service.getMaterialUsagePrediction();
        assertEquals(22, map.size());
    }

    @Test
    public void getALLMaterialBelow() {
        Map<String, Float> map = service.getALLMaterialBelow(Integer.MAX_VALUE);
        assertEquals(22, map.size());
    }

    @Test
    public void orderMaterial() {
        MaterialOrder materialOrder = service.orderMaterial("test", "beef", 10);
        materialOrder = materialOrderRepository.findByOperationOrderID(materialOrder.getOperationOrderID());
        assertEquals("beef", materialOrder.getMaterialName());
        assertEquals(10, materialOrder.getMaterialAmount(), 0);

        materialOrder = service.orderMaterial("test", "potato", 100);
        materialOrder = materialOrderRepository.findByOperationOrderID(materialOrder.getOperationOrderID());
        assertEquals("potato", materialOrder.getMaterialName());
        assertEquals(100, materialOrder.getMaterialAmount(), 0);
    }

    @Test
    public void findMaterialOrder() {
        List<MaterialOrder> materialOrders = service.findMaterialOrder("potato", new Timestamp(System.currentTimeMillis()-10000), new Timestamp(System.currentTimeMillis()+1000));
        assertEquals(2, materialOrders.size());
    }

    @Test
    public void ensureMaterialOrder() {
        MaterialOrder materialOrder = service.orderMaterial("test", "beef", 10);
        service.ensureMaterialOrder(materialOrder.getOperationOrderID(), "testNote");
        assertNotNull(materialOrderRepository.findByOperationOrderID(materialOrder.getOperationOrderID()).getOperationStorageID());
    }

    @Test
    public void findMaterialOrderOutOfDate() {
        List<MaterialOrder> materialOrderList = service.findMaterialOrderOutOfDate(1);
        assertEquals(3, materialOrderList.size());
    }

    @Test
    public void addRecipeForStall() {
        service.addRecipeForStall("川菜", "小笼包", "蛋炒饭");
        Stall stall = stallRepository.findByStallName("川菜");
        assertEquals(3, stall.getRecipes().size());
    }

    @Test
    public void removeRecipeFromStall() {
        service.addRecipeForStall("川菜", "小笼包", "蛋炒饭");
        service.removeRecipeFromStall("川菜", "小笼包");
        Stall stall = stallRepository.findByStallName("川菜");
        assertEquals(2, stall.getRecipes().size());
    }

    @Test
    public void getRecipeInOrderBySellingDuring() {
        List<Object[]> recipeList = service.getRecipeInOrderBySellingDuring(Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00"));
        assertEquals(5, recipeList.size());
        long temp = Integer.MAX_VALUE;
        for (Object[] e : recipeList) {
            assertNotNull(((Recipe)e[0]).getRecipeName());
            assertTrue(temp>=(long)e[1]);
            temp = (long) e[1];
        }
    }

    @Test
    public void getTotalSalesDuring() {
        assertEquals(8, service.getTotalSalesDuring(Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00")));
        assertEquals(2, service.getTotalSalesDuring("包子铺", Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00")));
    }

    @Test
    public void getStallInOrderBySellingBetweenDate() {
        List<Object[]> stallList = service.getStallInOrderBySellingBetweenDate(Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00"));
        assertEquals(5, stallList.size());
        long temp = Integer.MAX_VALUE;
        for (Object[] e : stallList) {
            assertNotNull(((Stall)e[0]).getStallName());
            assertTrue(temp>=(long)e[1]);
            temp = (long) e[1];
        }
    }

    @Test
    public void getStallUsageDuring() {
        List<Object[]> usageList = service.getStallUsageDuring(Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00"));
        assertEquals(6, usageList.size());
        usageList = service.getStallUsageDuring("兰州拉面", Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00"));
        assertEquals(2, usageList.size());
    }

    @Test
    public void getStallProfitDuring() {
        assertEquals(10, service.getStallProfitDuring("包子铺", Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00")),0);
    }

    @Test
    public void getStallRent() {
        assertEquals(3000, service.getStallRent("兰州拉面"), 0);
    }

    @Test
    public void getTransactionRecordDuring() {
        Collection<TransactionRecord> records = service.getTransactionRecordDuring("包子铺", Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.valueOf("2020-12-01 00:00:00"));
        assertEquals(2, records.size());
    }

    @Test
    public void saveRecipe() {
        service.saveRecipe("测试菜谱", 10, "beef", "blood");
        assertNotNull(recipeRepository.findByRecipeName("测试菜谱"));
    }

    @Test
    public void modifyRecipe() {
        service.saveRecipe("测试菜谱", 10, "beef", "blood");
        service.modifyRecipe("测试菜谱", 11, "beef", "blood", "cabbage");
        assertEquals(3, recipeRepository.findByRecipeName("测试菜谱").getMaterials().size());
    }

    @Test
    public void removeRecipe() {
        service.saveRecipe("测试菜谱", 10, "beef", "blood");
        service.removeRecipe("测试菜谱");
        assertNull(recipeRepository.findByRecipeName("测试菜谱"));
        assertNotNull(materialRepository.findByName("beef"));
    }

    @Test
    public void getStaffByID() {
        assertNotNull(service.getStaffByID(staffRepository.findALLByStaffName("none").get(0).getStaffID()));
    }

    @Test
    public void getOperationRecord() {
        assertEquals(11, service.getOperationRecord(staffRepository.findALLByStaffName("manager1").get(0).getStaffID()).size());
    }

    @Test
    public void getScheduleRecord() {
        assertEquals(1, service.getScheduleRecord(staffRepository.findALLByStaffName("cleaner4").get(0).getStaffID(), Timestamp.valueOf("2000-01-01 00:00:00")).size());
    }

    @Test
    public void scheduleStaff() {
        Staff staff = staffRepository.findALLByStaffName("cleaner4").get(0);
        service.scheduleStaff(Timestamp.valueOf("2020-05-20 00:00:00"), Timestamp.valueOf("2020-05-21 00:00:00"), staff.getStaffID(), "clean the floor");
        List<ScheduleRecord> scheduleRecordList = service.getScheduleRecord(staffRepository.findALLByStaffName("cleaner4").get(0).getStaffID(), Timestamp.valueOf("2000-01-01 00:00:00"));
        assertEquals(2, scheduleRecordList.size());
        assertEquals(scheduleRecordList.get(0).getStaff().getStaffID(), staff.getStaffID());
        assertTrue(scheduleRecordList.get(0).getTimeScheduledToStartWorking().equals(Timestamp.valueOf("2020-05-20 00:00:00")));
    }

//    @Test
//    public void getOwnID() {
//    }
//
//    @Test
//    public void getOwnCategoryType() {
//    }
//
//    @Test
//    public void getOwnOperationRecord() {
//    }
//
//    @Test
//    public void getOwnScheduleRecord() {
//    }
//
//    @Test
//    public void getMaterialForStall() {
//    }
//
//    @Test
//    public void stallSell() {
//    }
//
//    @Test
//    public void addRecipe() {
//    }
//
//    @Test
//    public void saveRecipe() {
//    }
//
//    @Test
//    public void removeRecipe() {
//    }
//
//    @Test
//    public void changePassword() {
//    }
}