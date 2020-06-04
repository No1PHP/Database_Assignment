package dao.DAOInterfaces;

import dao.tables.Material;
import dao.tables.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {
    Recipe findByRecipeName(String name);

    List<Recipe> findALLByPriceBetween(float min, float max);

    @Query(value = "select r, sum(t.numbers) " +
            "from Recipe r " +
            "left join TransactionRecord t with r.recipeName = t.recipeName " +
            "where t.transactionTime >= ?1 " +
            "and t.transactionTime <= ?2 " +
            "group by r.recipeName " +
            "order by sum(t.numbers) desc")
    List<Object[]> findSalesOrderDuring(Timestamp from, Timestamp to);

    List<Recipe> findALLByMaterialsContains(Material material);
}
