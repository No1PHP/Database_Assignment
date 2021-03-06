package controller.contImp;

import common.HttpServletRequestUtils;
import controller.model.Material;
import controller.model.MaterialAllocate;
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

@CrossOrigin(allowCredentials = "true")
@RequestMapping(value = "/Material") //此类url前缀
@Controller
public class MaterialController {
    /**
     *{materialName:'',materialType:'',unitPrice:'Float',availablePeriod:'',operationName:''}
     * @param request
     * @return
     */
    @RequestMapping(value = "/operate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doOperation(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (LOGIN_STATUS) {
            try {
                Material material = HttpServletRequestUtils.getModel(request, "materialRequest", Material.class);
                switch (material.getOperation()) {
                    //saveMaterial(String name, MaterialTypes types, float unitPrice, float availablePeriod)
                    case "Add":
                    case "Modify":
                        SERVICE.saveMaterial(material.getName(), MaterialTypes.valueOf(material.getType()), material.getUnitPrice(), material.getAvailablePeriod());
                        break;
                    default: throw new Exception("wrong operation type code!");
                }
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

    /**
     *{materialName:'',stallName:'',amount:'Float'}
     * @param request
     * @return
     */
    @RequestMapping(value = "/allocate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> allocate(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (LOGIN_STATUS) {
            try {
                MaterialAllocate allocate = HttpServletRequestUtils.getModel(request, "allocateRequest", MaterialAllocate.class);
                SERVICE.getMaterialForStall(allocate.getMaterialName(), allocate.getStallName(), allocate.getAmount());
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
