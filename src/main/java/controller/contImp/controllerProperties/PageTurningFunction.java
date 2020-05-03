package controller.contImp.controllerProperties;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @description interface implemented by those who have page changing involved function
 * @note 翻页功能，可能会加筛选功能
 * @create 2020-05-03-02-12
 **/
public interface PageTurningFunction {

    Map<String, Object> pageTurning(@RequestParam int currentPage, @RequestParam  int size);


}
