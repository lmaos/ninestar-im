package org.ninestar.example.quickstart.server.controller;

import org.ninestar.example.common.PackageState;
import org.ninestar.im.server.controller.ann.NineStarSerController;
import org.ninestar.im.server.controller.ann.NineStarSerUri;
import org.ninestar.im.server.controller.ann.RequestHeader;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Request;
import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;
import org.springframework.web.bind.annotation.RequestParam;

@NineStarSerController
@NineStarSerUri("/quickStart")
public class QuickStartController {

    @NineStarSerUri("/hello")
    public void hello(
        @RequestParam("userName") String userName,
        @RequestHeader("age") Integer age,
        NineStarImMsgSerV0Request request,
        NineStarImMsgSerV0Response response){

        System.out.println("Server: receive param userName -> " + userName);
        System.out.println("Server: receive param age -> " + age);

        response.setMsg("welcome!");
        response.setState(PackageState.SUCCESS);
        response.putBoolean(true);
        response.putFloat(100L);

        String body = request.bodyToString();
        System.out.println("Server: receive body -> " + body);
    }

}
