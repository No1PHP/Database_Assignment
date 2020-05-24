import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.enums.MaterialTypes;
import dao.enums.StaffCategoryTypes;
import dao.tables.Material;
import dao.tables.Recipe;
import dao.tables.Staff;
import dao.tables.Stall;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DataInitializer;
import service.Service;
import service.exceptions.IllegalRequestException;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

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
    public Service adminService;
    public Service materialService;
    public Service staffService;
    public Service stallService;


    @Before
    public void setUp() throws Exception {
        DataInitializer.clear();
        DataInitializer.run();
        adminService = Service.connect("admin", "admin");
        materialService = Service.connect("storeroomClerk1", "ccc");
        staffService = Service.connect("manager1", "aaa");
        stallService = Service.connect("manager2", "bbb");
        System.out.println("finish init");
    }

    @Test
    public void saveMaterial() {
        adminService.saveMaterial("testMaterial1", MaterialTypes.MEAT, 1.1f, 4.2f);
        assertNotNull(materialRepository.findByName("testMaterial1"));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void removeMaterial() {
        adminService.removeMaterial("corn");
        assertNull(materialRepository.findByName("corn"));
    }

    @Test
    public void saveStall() {
        List<Stall> stallList = stallRepository.findAll(Sort.by("stallLocation").descending());
        adminService.saveStall("testStall", stallList.get(0).getStallLocation()+1, 1000);
        assertNotNull(stallRepository.findByStallName("testStall"));
    }

    @Test
    public void saveStallWithRecipes() {
        List<Stall> stallList = stallRepository.findAll(Sort.by("stallLocation").descending());
        List<Recipe> recipes = new LinkedList<>();
        recipes.add(recipeRepository.findByRecipeName("小笼包"));
        recipes.add(recipeRepository.findByRecipeName("馄饨"));
        recipes.add(recipeRepository.findByRecipeName("蛋炒饭"));

        adminService.saveStallWithRecipes("testStall", stallList.get(0).getStallLocation()+1, 1000, recipes);
        Stall stall = stallRepository.findByStallName("testStall");
        assertNotNull(stall);
        assertEquals(stall.getRecipes().size(), recipes.size());
    }

    @Test
    public void removeStall() {
        adminService.removeStall("披萨");
        assertNull(stallRepository.findByStallName("披萨"));
    }

    @Test
    public void insertStaff() {
        Staff staff = adminService.insertStaff("testStaff", StaffCategoryTypes.CLEANER, Time.valueOf("07:00:00"), Time.valueOf("15:00:00"));
        assertNotNull(staffRepository.findByStaffID(staff.getStaffID()));
    }

    @Test
    public void updateStaff() {
        Staff staff = adminService.insertStaff("testStaff", StaffCategoryTypes.CLEANER, Time.valueOf("07:00:00"), Time.valueOf("15:00:00"));
        adminService.updateStaff(staff.getStaffID(), StaffCategoryTypes.STOREROOM_CLERK, Time.valueOf("07:00:00"), Time.valueOf("15:00:00"));
        assertEquals((long)staffRepository.findByStaffID(staff.getStaffID()).getStaffCategory(), StaffCategoryTypes.STOREROOM_CLERK.ordinal());
    }

    @Test
    public void removeStaff() {
        adminService.removeStaff(staffRepository.findALLByStaffName("storeroomClerk4").get(0).getStaffID());
        assertEquals(staffRepository.findALLByStaffName("storeroomClerk4").size(), 0);
    }
//
//    @Test
//    public void saveAccount() {
//    }
//
//    @Test
//    public void saveAccessInfo() {
//    }
//
//    @Test
//    public void removeAccessInfo() {
//    }
//
//    @Test
//    public void getMaterialAvailableAmount() {
//    }
//
//    @Test
//    public void getALLMaterialUsageBetween() {
//    }
//
//    @Test
//    public void getMaterialUsageBetween() {
//    }
//
//    @Test
//    public void getMaterialUsagePrediction() {
//    }
//
//    @Test
//    public void testGetMaterialUsagePrediction() {
//    }
//
//    @Test
//    public void getALLMaterialBelow() {
//    }
//
//    @Test
//    public void orderMaterial() {
//    }
//
//    @Test
//    public void findMaterialOrder() {
//    }
//
//    @Test
//    public void ensureMaterialOrder() {
//    }
//
//    @Test
//    public void findMaterialOrderOutOfDate() {
//    }
//
//    @Test
//    public void testFindMaterialOrderOutOfDate() {
//    }
//
//    @Test
//    public void testFindMaterialOrderOutOfDate1() {
//    }
//
//    @Test
//    public void addRecipeForStall() {
//    }
//
//    @Test
//    public void removeRecipeFromStall() {
//    }
//
//    @Test
//    public void getRecipeInOrderBySellingDuring() {
//    }
//
//    @Test
//    public void getTotalSalesDuring() {
//    }
//
//    @Test
//    public void testGetTotalSalesDuring() {
//    }
//
//    @Test
//    public void getStallInOrderBySellingBetweenDate() {
//    }
//
//    @Test
//    public void getStallUsageDuring() {
//    }
//
//    @Test
//    public void testGetStallUsageDuring() {
//    }
//
//    @Test
//    public void getStallProfitDuring() {
//    }
//
//    @Test
//    public void getStallRent() {
//    }
//
//    @Test
//    public void getTransactionRecordDuring() {
//    }
//
//    @Test
//    public void getStaffByID() {
//    }
//
//    @Test
//    public void getOperationRecord() {
//    }
//
//    @Test
//    public void getScheduleRecord() {
//    }
//
//    @Test
//    public void scheduleStaff() {
//    }
//
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