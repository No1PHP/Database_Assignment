package dao.DAOInterfaces;

import dao.tables.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>, JpaSpecificationExecutor<Staff> {
    Staff findByStaffID(int id);

    List<Staff> findALLByStaffName(String name);

    List<Staff> findALLByStaffNameLike(String name);

    List<Staff> findALLByStaffCategory(byte type);

    List<Staff> findALLByTimeStartWorkingBetween(Time min, Time max);

    List<Staff> findALLByTimeEndWorkingBetween(Time min, Time max);
}
