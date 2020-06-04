package controller.contImp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static constants.globalConstants.LOGIN_STATUS;
import static constants.globalConstants.SERVICE;

@CrossOrigin(allowCredentials = "true")
@Controller
public class Find_removeController {

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAll(@RequestParam("pageNo") Integer pageNo, @RequestParam("size") Integer size, @RequestParam("page") String name) {
        Map<String, Object> map = new HashMap<>();
        if (LOGIN_STATUS) {
            try {
                map.put("result", SERVICE.getALL(name, pageNo, size));
                map.put("totalPage", SERVICE.getTotalPage(name, size));
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

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> removeByID(@RequestParam("id") String id, @RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<>();
        if (LOGIN_STATUS) {
            try {
                SERVICE.removeByID(id, name);
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
