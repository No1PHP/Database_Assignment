package controller.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @description staff information class for controller
 * @create 2020-05-02-23-27
 **/
@Getter
@Setter
public class Stall {
    public Integer stallID;

    public String stallName;

    public String stallLocation;

    public Float stallRent;

    public Float costLastMonth;

    public Integer manageTimeSoFar;

    public Float aveMonthlySalesAmount;

    public String operationName;


}
