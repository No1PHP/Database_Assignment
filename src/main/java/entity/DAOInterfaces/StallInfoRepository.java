package entity.DAOInterfaces;

import entity.tables.StallInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StallInfoRepository extends JpaRepository<StallInfo, Integer>, JpaSpecificationExecutor<StallInfo> {
}
