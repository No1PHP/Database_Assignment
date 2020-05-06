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

    List<Staff> findByStaffName(String name);

    List<Staff> findByStaffNameLike(String name);

    List<Staff> findByStaffCategory(byte type);

    List<Staff> findByTimeStartWorkingBetween(Time min, Time max);

    List<Staff> findByTimeEndWorkingBetween(Time min, Time max);
}
