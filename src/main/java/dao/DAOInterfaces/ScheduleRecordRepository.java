package dao.DAOInterfaces;

import dao.tables.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Integer>, JpaSpecificationExecutor<ScheduleRecord> {
    ScheduleRecord findByOperationID(int id);

    List<ScheduleRecord> findFirst20ByStaffIDOrderByTimeScheduledToStartWorkingDesc(int id);

    List<ScheduleRecord> findFirst20ByStaffIDOrderByTimeScheduledToEndWorkingDesc(int id);

    List<ScheduleRecord> findByStaffIDAndTimeScheduledToStartWorkingAfterOrderByTimeScheduledToStartWorkingDesc(int id, Timestamp min);
}
