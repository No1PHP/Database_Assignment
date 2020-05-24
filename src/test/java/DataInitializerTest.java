import dao.DAOInterfaces.*;
import dao.DAO_Type;
import dao.tables.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.DataInitializer;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DataInitializerTest {

    @Test
    public void run() {
        DataInitializer.run();
    }

    @Test
    @Transactional
    @Rollback(false)
    public void clear() {
        DataInitializer.clear();
    }
}
