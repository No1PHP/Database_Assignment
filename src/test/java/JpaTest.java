import dao.DAOInterfaces.AccessInfoRepository;
import dao.DAOInterfaces.StaffRepository;
import dao.DAO_Type;
import dao.tables.AccessInfo;
import dao.tables.Staff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Time;
import java.util.List;

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

        ((StaffRepository) DAO_Type.STAFF.getTableRepository()).save(staff);
    }

    @Test
    public void getAccessInfo() {
        AccessInfoRepository accessInfoRepository = (AccessInfoRepository) DAO_Type.ACCESS_INFO.getTableRepository();
        AccessInfo accessInfo = accessInfoRepository.findByPosition("admin");
        System.out.println(accessInfo);
        System.out.println();
        List<AccessInfo> list = accessInfoRepository.findByAccessToOrder(true);
        for (AccessInfo e : list) {
            System.out.println(e);
        }
    }
}
