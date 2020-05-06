package dao.DAOInterfaces;

import dao.tables.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {
    Recipe findByRecipeID(int id);

    Recipe findByRecipeName(String name);

    List<Recipe> findByRelevantIngredientContains(String material);

    List<Recipe> findByPriceBetween(float min, float max);
}
