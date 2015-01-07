package notification.controller;

import hackaton.communication.exceptions.KeystoreException;
import hackaton.notification.AppleNotificationServerBasicImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uchegc01 on 05/01/2015.
 */

@Controller
@RequestMapping(value = "/test-link")
public class TestController {

    @RequestMapping(value = "/show",method = RequestMethod.GET,headers="Accept=application/xml, application/json")
    @ResponseBody
    public Object show()throws KeystoreException{

        List<String> names = new ArrayList<String>();

        Object keystore=""; String password=""; boolean production=true;
        AppleNotificationServerBasicImpl notificationServer = new AppleNotificationServerBasicImpl(keystore,password,production);
        notificationServer.getNotificationServerHost();
        names.add("Gugu");
        names.add("Sibahle");
        names.add("Ugo");
        return names;
    }
}
