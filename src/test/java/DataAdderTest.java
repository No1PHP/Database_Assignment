import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.EntityFactor;
import dao.tables.ScheduleRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DataAdder;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DataAdderTest {

    @Test
    @Transactional
    public void run() {
        clearAll();
        DataAdder.run();
    }

    @Test
    public void clearAll() {
        ScheduleRecordRepository scheduleRecordRepository = (ScheduleRecordRepository) DAO_Type.SCHEDULE_RECORD.getTableRepository();
        MaterialOrderRepository materialOrderRepository = (MaterialOrderRepository) DAO_Type.MATERIAL_ORDER.getTableRepository();
        OperationRecordRepository operationRecordRepository = (OperationRecordRepository) DAO_Type.OPERATION_RECORD.getTableRepository();
        AccountRepository accountRepository = (AccountRepository) DAO_Type.ACCOUNT.getTableRepository();
        StaffRepository staffRepository = (StaffRepository) DAO_Type.STAFF.getTableRepository();
        AccessInfoRepository accessInfoRepository = (AccessInfoRepository) DAO_Type.ACCESS_INFO.getTableRepository();
        TransactionRecordRepository transactionRecordRepository = (TransactionRecordRepository) DAO_Type.TRANSACTION_RECORD.getTableRepository();
        MaterialUsageRepository materialUsageRepository = (MaterialUsageRepository) DAO_Type.MATERIAL_USAGE.getTableRepository();
        RecipeRepository recipeRepository = (RecipeRepository) DAO_Type.RECIPE.getTableRepository();
        MaterialRepository materialRepository = (MaterialRepository) DAO_Type.MATERIAL.getTableRepository();
        StallRepository stallRepository = (StallRepository) DAO_Type.STALL.getTableRepository();

        scheduleRecordRepository.deleteAll();
        materialOrderRepository.deleteAll();
        operationRecordRepository.deleteAll();
        accountRepository.deleteAll();
        staffRepository.deleteAll();
        accessInfoRepository.deleteAll();
        transactionRecordRepository.deleteAll();
        materialUsageRepository.deleteAll();
        recipeRepository.deleteAll();
        materialRepository.deleteAll();
        stallRepository.deleteAll();
    }
}
