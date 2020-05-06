package dao.DAOInterfaces;

import dao.tables.Stall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StallRepository extends JpaRepository<Stall, Integer>, JpaSpecificationExecutor<Stall> {
    Stall findByStallID(int id);

    Stall findByStallName(String name);

    List<Stall> findByStallNameLike(String name);

    Stall findByStallLocation(int location);

    List<Stall> findByStallRentBetween(float min, float max);

    List<Stall> findByCostLastMonthBetween(float min, float max);

    List<Stall> findByManageTimeSoFarBetween(int min, int max);

    List<Stall> findByAveMonthlySalesAmountBetween(float min, float max);

    List<Stall> findByAveSalesIncomeBetween(float min, float max);
}
