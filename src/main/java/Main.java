import dao.DAOInterfaces.StaffRepository;
import dao.DAO_Type;
import dao.tables.Staff;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import service.DataAdder;

import java.sql.Time;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Service
public class Main {

    public static void main(String args[]) {
        DataAdder.run();
    }
}
