package dao.DAOInterfaces;

import dao.tables.MaterialUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, Integer>, JpaSpecificationExecutor<Integer> {
    MaterialUsage findByUsageId(int id);

    List<MaterialUsage> findALLByStallID(int id);

    List<MaterialUsage> findALLByMaterialID(int id);

    List<MaterialUsage> findAllByTimeBetween(Date from, Date to);

    @Query(value = "select SUM(amount) from MaterialUsage where materialID = ?1 and time >= ?2 and time <= ?3")
    Float getTotalUsageByTimeBetween(int materialID, Date from, Date to);
}
