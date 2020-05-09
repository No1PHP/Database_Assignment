package dao.DAOInterfaces;

import dao.tables.RecipeUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeUsageRepository extends JpaRepository<RecipeUsage, Integer>, JpaSpecificationExecutor<Integer> {
    RecipeUsage findByUsageId(int id);

    List<RecipeUsage> findALLByStallID(int id);

    List<RecipeUsage> findALLByMaterialID(int id);
}
