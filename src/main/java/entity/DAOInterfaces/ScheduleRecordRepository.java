package entity.DAOInterfaces;

import entity.tables.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Integer>, JpaSpecificationExecutor<ScheduleRecord> {
}
