package entity.DAOInterfaces;

import entity.tables.AccessInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessInfoRepository extends JpaRepository<AccessInfo, String>, JpaSpecificationExecutor<AccessInfo> {
}
