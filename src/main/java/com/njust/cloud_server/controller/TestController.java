package com.njust.cloud_server.controller;

import com.njust.cloud_server.paillier.PaillierKeyGenerator;
import com.njust.cloud_server.paillier.PaillierPrivateKey;
import com.njust.cloud_server.paillier.PaillierPublicKey;
import com.njust.cloud_server.dao.ADDao;
import com.njust.cloud_server.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用Controller
 */
@RestController
public class TestController {
    @Autowired
    private ADDao adDao;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/test")
    public String test(){
        PaillierKeyGenerator pkg = new PaillierKeyGenerator();
        PaillierPublicKey publicKey = PaillierPublicKey.paillierJsonToPublicKey("{\"n\":7037996759611275900405487329144489085210900622405788623915340046554895678557675360099993502545810105916795350348201798995744651664108236879690390748857833,\"nSquare\":49533398388298819693190911443085500113137594389227717398938303574532356291531019850234314622241175041250992063305927006862844026670633749958420794136365527887009273250901790502746504678689585917463571409706569379921923499464969602901871572009667889989146252127852333968575165007138552703354893437794045455889,\"g\":47,\"bitLength\":512,\"timeStamp\":1580452220178}");
        PaillierPrivateKey privateKey = pkg.getPaillierPrivateKey();
        System.out.println(privateKey.getTimeStamp());
        System.out.println(publicKey.getTimeStamp());
        System.out.println(publicKey.isTimeUp());
        return "hi";
    }
}
