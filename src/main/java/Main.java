import entity.DAOInterfaces.StaffRepository;
import entity.TableType;
import entity.tables.Staff;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Time;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Service
public class Main {

    public static void main(String args[]) {
        Staff staff = new Staff();
        staff.setStaffName("Admin");
        staff.setStaffCategory((byte) 1);
        staff.setTimeStartWorking(new Time(0,0,0));
        staff.setTimeEndWorking(new Time(0,0,0));

        ((StaffRepository)TableType.STAFF.getTableRepository()).save(staff);
    }
}
