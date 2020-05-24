package dao.DAOInterfaces;

import dao.tables.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MaterialOrderRepository extends JpaRepository<MaterialOrder, Integer>, JpaSpecificationExecutor<MaterialOrder> {
    MaterialOrder findByOperationOrderID(int operationOrderID);

    MaterialOrder findByOperationStorageID(int operationStorageID);

    List<MaterialOrder> findALLByMaterialName(String materialName);

    @Query(value = "select sum(o.materialAmount) from MaterialOrder o " +
            "join OperationRecord r with o.operationStorageID = r.operationID " +
            "join Material m with o.materialName = m.name " +
            "where o.materialName = ?1 " +
            "and r.operationTime > ?2")
    Float findAvailableByName(String materialName, Timestamp limit);

    @Query(value = "select o from MaterialOrder o " +
            "join OperationRecord r with o.operationStorageID = r.operationID " +
            "join Material m with o.materialName = m.name " +
            "where o.materialName = ?1 " +
            "and r.operationTime < ?2")
    List<MaterialOrder> findAllOutOfDate(String materialName, Timestamp limit);
}
