package dao.DAOInterfaces;

import dao.tables.OperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OperationRecordRepository extends JpaRepository<OperationRecord, Integer>, JpaSpecificationExecutor<OperationRecord> {
    OperationRecord findByOperationID(int id);

    List<OperationRecord> findALLByStaffID(int id);

    List<OperationRecord> findALLByStaffIDOrderByOperationTime(int id);

    List<OperationRecord> findALLByOperationType(byte type);

    List<OperationRecord> findALLByOperationTypeAndOperationTimeBetween(byte type, Date time1, Date time2);

    List<OperationRecord> findALLByNoteLike(String value);

    List<OperationRecord> findALLByOperationTimeBetween(Date time1, Date time2);
}
