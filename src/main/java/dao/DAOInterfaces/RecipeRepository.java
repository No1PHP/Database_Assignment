package dao.DAOInterfaces;

import dao.tables.Recipe;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {
    Recipe findByRecipeID(int id);

    Recipe findByRecipeName(String name);

    List<Recipe> findByRelevantIngredientContains(String material);

    List<Recipe> findByPriceBetween(float min, float max);
}
