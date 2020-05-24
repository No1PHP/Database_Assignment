package dao.DAOInterfaces;

import dao.tables.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Integer>, JpaSpecificationExecutor<TransactionRecord> {
    TransactionRecord findByTransactionID(int id);

    List<TransactionRecord> findALLByStallName(String name);

    List<TransactionRecord> findALLByRecipeName(String name);

    List<TransactionRecord> findALLByTransactionTimeBetween(Timestamp from, Timestamp to);

    List<TransactionRecord> findALLByStallNameAndTransactionTimeBetween(String name, Timestamp from, Timestamp to);

    @Query(value = "select SUM(numbers) from TransactionRecord where transactionTime >= ?1 and transactionTime <= ?2")
    Integer findALLTotalSalesByTransactionTimeBetween(Timestamp from, Timestamp to);

    @Query(value = "select SUM(numbers) from TransactionRecord where stallName = ?1 and transactionTime >= ?2 and transactionTime <= ?3")
    Integer findALLTotalSalesByStallNameAndTransactionTimeBetween(String name, Timestamp from, Timestamp to);

    @Query(value = "select SUM(transactionPrice) from TransactionRecord where stallName = ?1 and transactionTime >= ?2 and transactionTime <= ?3")
    Float getProfitOfStallDuring(String stallName, Timestamp from, Timestamp to);
}
