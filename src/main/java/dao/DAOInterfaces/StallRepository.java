package dao.DAOInterfaces;

import dao.tables.Stall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface StallRepository extends JpaRepository<Stall, Integer>, JpaSpecificationExecutor<Stall> {
    Stall findByStallName(String name);

    List<Stall> findALLByStallNameLike(String name);

    Stall findByStallLocation(int location);

    List<Stall> findALLByStallRentBetween(float min, float max);

    List<Stall> findALLByCostLastMonthBetween(float min, float max);

    List<Stall> findALLByManageTimeSoFarBetween(int min, int max);

    List<Stall> findALLByAveMonthlySalesAmountBetween(float min, float max);

    List<Stall> findALLByAveSalesIncomeBetween(float min, float max);

    @Query(value = "select s, sum(t.numbers) from Stall s left join TransactionRecord t with s.stallName = t.stallName where t.transactionTime >= ?1 and t.transactionTime <= ?2 group by t.stallName order by sum(t.numbers)")
    List<Object[]> findSalesDuring(Timestamp from, Timestamp to);
}
