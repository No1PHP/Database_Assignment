import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.Service;

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ServiceTest {
    @Test
    public void run1() {
        /*
         select *
         from Account account0_
         where account0_.accountName=?

         select *
         from AccessInfo accessinfo0_
         where accessinfo0_.position=?

         select *
         from Staff staff0_
         left outer join Account account1_ on staff0_.staff_id=account1_.staffId
         left outer join AccessInfo accessinfo2_ on account1_.position=accessinfo2_.position
         where staff0_.staff_id=?
         */
        Service service = Service.connect("admin", "admin");

        /*
        select recipe0_.recipeName as col_0_0_, sum(transactio1_.numbers) as col_1_0_, recipe0_.recipeName as recipena1_6_, recipe0_.price as price2_6_
        from Recipe recipe0_
        left outer join TransactionRecord transactio1_ on (recipe0_.recipeName=transactio1_.recipeName)
        where transactio1_.TransactionTime>=? and transactio1_.TransactionTime<=?
        group by recipe0_.recipeName
        order by sum(transactio1_.numbers)
         */
        assert service != null;
        service.getRecipeInOrderBySellingDuring(new Timestamp(System.currentTimeMillis() - 86400000), new Timestamp(System.currentTimeMillis()));


    }
}
