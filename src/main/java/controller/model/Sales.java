package controller.model;

import dao.tables.Stall;

import java.sql.Timestamp;

/**
 * @description transaction related request
 * @create 2020-05-03-02-08
 **/
public class Sales{
    private Integer transactionID;

    private String stallName;

    private String recipeName;

    private Timestamp transactionTime;

    private Integer numbers = 1;

    private Float transactionPrice;

    private Recipe recipe;

    private Stall stall;

}
