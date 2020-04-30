import entity.DAOInterfaces.StaffRepository;
import entity.TableType;
import entity.tables.Staff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Time;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpaTest {

    @Test
    public void addStaff() {
        Staff staff = new Staff();
        staff.setStaffName("Admin");
        staff.setStaffCategory((byte) 1);
        staff.setTimeStartWorking(new Time(0,0,0));
        staff.setTimeEndWorking(new Time(0,0,0));

        ((StaffRepository) TableType.STAFF.getTableRepository()).save(staff);
    }
}
