package dao.DAOInterfaces;

import dao.tables.OperationRecord;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Repository
public interface OperationRecordRepository extends JpaRepository<OperationRecord, Integer>, JpaSpecificationExecutor<OperationRecord> {
    OperationRecord findByOperationID(int id);

    List<OperationRecord> findByStaffID(int id);

    List<OperationRecord> findByStaffIDOrderByOperationTime(int id);

    List<OperationRecord> findByOperationType(byte type);

    List<OperationRecord> findByOperationTypeOrderByOperationTime(byte type);

    List<OperationRecord> findByNoteLike(String value);

    List<OperationRecord> findByOperationTimeBetween(Date time1, Date time2);
}
