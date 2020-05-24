package dao.DAOInterfaces;

import dao.tables.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer>, JpaSpecificationExecutor<Material> {
    Material findByName(String name);

    List<Material> findALLByType(byte type);

    List<Material> findALLByUnitPriceBetween(float min, float max);

    Material removeByName(String name);
}
