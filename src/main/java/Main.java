import entity.enums.StaffCategoryTypes;
import entity.tables.Account;
import entity.tables.Staff;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Date;
import java.sql.Time;

public class Main {

    @Test
    public void addAccount() {
        //加载配置，创建工厂
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJPA");
        //获取管理器
        EntityManager manager = factory.createEntityManager();
        //获取事务对象
        EntityTransaction transaction = manager.getTransaction();
        //开启事务
        transaction.begin();

        Staff staff = new Staff();
        staff.setStaffName("Admin");
        staff.setTimeEndWorking(new Time(0,0,0));
        staff.setTimeStartWorking(new Time(0,0,0));
        staff.setStaffCategory(StaffCategoryTypes.ADMIN);
        manager.persist(staff);
        //manager.merge()更新
        //manager.remove();删除
        //查找
        //manager.find();
        //manager.getReference()
        //提交事务
        transaction.commit();
        //释放资源
        manager.close();
        factory.close();
    }

    public static void main(String args[]) {
        System.out.printf("111");
    }
}
