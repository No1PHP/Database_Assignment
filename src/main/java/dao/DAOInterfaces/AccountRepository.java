package dao.DAOInterfaces;

import dao.tables.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    Account findByStaffID(int id);

    Account findByAccountName(String name);

    List<Account> findALLByPosition(String position);
}
