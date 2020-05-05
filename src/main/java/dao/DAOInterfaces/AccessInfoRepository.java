package dao.DAOInterfaces;

import dao.tables.AccessInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessInfoRepository extends JpaRepository<AccessInfo, String>, JpaSpecificationExecutor<AccessInfo> {
    //find accessInfo by position
    AccessInfo findByPosition(String position);

    //find all access by accessToOrder attribute
    List<AccessInfo> findByAccessToOrder(boolean accessToOrder);

    //find all access by accessToStaff attribute
    List<AccessInfo> findByAccessToStaff(boolean accessToStaff);

    //find all access by accessToStock attribute
    List<AccessInfo> findByAccessToStock(boolean accessToStock);
}
