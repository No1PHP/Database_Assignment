package dao.DAOInterfaces;

import dao.tables.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialOrderRepository extends JpaRepository<MaterialOrder, Integer>, JpaSpecificationExecutor<MaterialOrder> {
}
