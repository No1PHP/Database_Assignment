package dao.DAOInterfaces;

import dao.tables.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Integer>, JpaSpecificationExecutor<TransactionRecord> {
    TransactionRecord findByTransactionID(int id);

    List<TransactionRecord> findALLByStallID(int id);

    List<TransactionRecord> findALLByRecipeID(int id);

    List<TransactionRecord> findALLByTransactionTimeBetween(Date from, Date to);

    @Query(value = "select SUM(numbers) from TransactionRecord where transactionTime >= ?1 and transactionTime <= ?2")
    Integer findALLTotalSalesByTransactionTimeBetween(Date from, Date to);
}
