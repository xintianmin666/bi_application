package com.carservice.project.wzcx;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wzcx")
public class WzcxController {
    @Autowired
    private TmriOutAccess tmriOutAccess;
    @RequestMapping("/addNewCar")
    public String addNewCar(String hpzl,String hphm,String fdjh,String sjhm){
        Map map1 = new HashMap();
        map1.put("hpzl",hpzl);
        map1.put("hphm",hphm);
        map1.put("fdjh",fdjh);
        map1.put("sjhm",sjhm);

        String param1 = ViolationUtil.getXmlFileHead()+
                ViolationUtil.bean2xmlUtf8(map1,"veh","")+
                ViolationUtil.getXmlFileFoot();
        String result1 = tmriOutAccess.getTmriOutAccessHttpSoap12Endpoint().writeObjectOut("09","4E9C94F0257F14D6E050007F0100BB76","09C55",param1);
        //System.out.println(ViolationUtil.decodeUtf8(result1));
        JSONObject jsonObject = ViolationUtil.xml2jsonString(ViolationUtil.decodeUtf8(result1));
        return jsonObject.getJSONObject("root").getJSONObject("head").toString();
    }
}
