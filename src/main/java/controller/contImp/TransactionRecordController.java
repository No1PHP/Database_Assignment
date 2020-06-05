package controller.contImp;

import common.HttpServletRequestUtils;
import controller.model.Material;
import controller.model.Transaction;
import dao.enums.MaterialTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

/**
 * @description
 * @create 2020-05-12-21-33
 **/
@CrossOrigin(allowCredentials = "true")
@RequestMapping(value = "/Transaction") //此类url前缀
@Controller
public class TransactionRecordController {
    /**
     *{transactionID:'',stallName:'',recipeName:'',transactionTime:'',numbers:'',transactionPrice:'',operation:''}
     * @param request
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doOperation(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (LOGIN_STATUS) {
            try {
                Transaction transaction = HttpServletRequestUtils.getModel(request, "transactionRequest", Transaction.class);
                SERVICE.saveTransactionRecord(transaction.getNumbers(), transaction.getTransactionPrice(), transaction.getRecipeName(), transaction.getStallName());
                map.put("succeed", true);
            } catch (Exception e) {
                map.put("succeed", false);
                map.put("message: ", e.getMessage());
            }
        } else {
            map.put("succeed", false);
            map.put("message", "please login first!");
        }
        return map;
    }

}
