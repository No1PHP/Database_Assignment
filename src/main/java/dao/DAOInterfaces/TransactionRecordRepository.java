package dao.DAOInterfaces;

import dao.tables.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Integer>, JpaSpecificationExecutor<TransactionRecord> {
    TransactionRecord findByTransactionID(int id);

    List<TransactionRecord> findByStallID(int id);

    List<TransactionRecord> findByRecipeID(int id);

    List<TransactionRecord> findByTransactionTimeBetween(Date min, Date max);
}
