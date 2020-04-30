package entity;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum TableType {
    ACCESS_INFO("accessInfo"),
    ACCOUNT("account"),
    MATERIAL("material"),
    MATERIAL_ORDER("materialOrder"),
    OPERATION_RECORD("operationRecord"),
    RECIPE("recipe"),
    SCHEDULE_RECORD("scheduleRecord"),
    STAFF("staff"),
    STALL_INFO("stallInfo"),
    TRANSACTION_RECORD(" transactionRecord");

    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    public final String name;
    private Object repository;

    TableType(String name) {
        this.name = name;
    }

    public Object getTableRepository() {
        if (repository == null)
            repository = applicationContext.getBean(name + "Repository");
        return repository;
    }
}
