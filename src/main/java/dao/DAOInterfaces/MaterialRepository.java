package dao.DAOInterfaces;

import dao.tables.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer>, JpaSpecificationExecutor<Material> {
    Material findById(int id);

    Material findByName(String name);

    List<Material> findByType(byte type);

    List<Material> findByUnitPriceBetween(float min, float max);

    List<Material> findByAvailableAmountBetween(float min, float max);
}
