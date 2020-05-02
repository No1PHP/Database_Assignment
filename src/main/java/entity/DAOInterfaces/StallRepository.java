package entity.DAOInterfaces;

import entity.tables.Stall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StallRepository extends JpaRepository<Stall, Integer>, JpaSpecificationExecutor<Stall> {
}
