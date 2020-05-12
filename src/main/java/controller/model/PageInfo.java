package controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @description designed for page turning&display information
 * @create 2020-05-02-21-53
 **/
@Getter
@Setter
@AllArgsConstructor
public class PageInfo {

    private int pageNo;
    private int size;


}
