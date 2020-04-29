package entity.DAOInterfaces;

import entity.tables.OperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRecordRepository extends JpaRepository<OperationRecord, Integer>, JpaSpecificationExecutor<OperationRecord> {
}
