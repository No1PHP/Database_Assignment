import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.tables.Material;
import dao.tables.Recipe;
import dao.tables.Stall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DataInitializer;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DataInitializerTest {
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

    @Test
    @Transactional
    public void run() {
        clearAll();
        DataInitializer.run();
    }

    @Test
    public void clearAll() {
        scheduleRecordRepository.deleteAll();
        materialOrderRepository.deleteAll();
        operationRecordRepository.deleteAll();
        accountRepository.deleteAll();
        staffRepository.deleteAll();
        accessInfoRepository.deleteAll();
        transactionRecordRepository.deleteAll();
        materialUsageRepository.deleteAll();

        List<Recipe> recipeList = recipeRepository.findAll();
        for (Recipe e : recipeList) {
            e.getMaterials().clear();
            e.getStalls().clear();
        }
        recipeRepository.saveAll(recipeList);
        recipeRepository.flush();

        List<Material> materialList = materialRepository.findAll();
        for (Material e : materialList) {
            e.getRecipes().clear();
        }
        materialRepository.saveAll(materialList);
        materialOrderRepository.flush();

        List<Stall> stallList = stallRepository.findAll();
        for (Stall e : stallList) {
            e.getRecipes().clear();
        }
        stallRepository.saveAll(stallList);
        stallRepository.flush();

        recipeRepository.deleteAll();
        materialRepository.deleteAll();
        stallRepository.deleteAll();
    }
}
