package dao.DAOInterfaces;

import dao.tables.Stall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

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
}
