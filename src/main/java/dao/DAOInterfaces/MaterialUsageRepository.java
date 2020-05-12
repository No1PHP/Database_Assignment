package dao.DAOInterfaces;

import dao.tables.MaterialUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, Integer>, JpaSpecificationExecutor<Integer> {
    MaterialUsage findByUsageId(int id);

    List<MaterialUsage> findALLByStallName(String name);

    List<MaterialUsage> findALLByMaterialName(String name);

    List<MaterialUsage> findAllByTimeBetween(Timestamp from, Timestamp to);

    @Query(value = "select SUM(amount) from MaterialUsage where materialName = ?1 and time >= ?2 and time <= ?3")
    Float getTotalUsageByTimeBetween(String materialName, Timestamp from, Timestamp to);
}
