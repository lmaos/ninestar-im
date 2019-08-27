package org.ninestar.example.quickstart.client;

import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Request;
import org.ninestar.im.client.handle_v0.NineStarImMsgCliV0Response;
import org.ninestar.im.client.handle_v0.NineStarImV0Output;

public class QuickStartClientDemo {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;


    public static void main(String[] args) {
        // 初始化客户端
        NineStarImClient client = new NineStarImClient(HOST, PORT);
        NineStarImV0Output output = client.getNineStarImV0Output();

        // 新建request请求
        String uri = "/quickStart/hello";
        NineStarImMsgCliV0Request request = new NineStarImMsgCliV0Request(uri);
        request.setHeaderValue("userName", "NINESTAR");
        request.setHeaderValue("age", "105");
        request.setBody("hello server!".getBytes());


        // 发送request请求并得到返回值response
        NineStarImMsgCliV0Response response = null;
        try {
            response = output.sendSync(request);
        } catch (NineStarCliRequestTimeoutException e) {
            e.printStackTrace();
        }


        // 读取返回response数据
        if(response == null){
            System.out.println("Client: response is null.");
            throw new RuntimeException("null response.");
        }
        String msg = response.getMsg();
        System.out.println("Client: receive response msg -> " + msg);
        Boolean boo = response.readBoolean();
        System.out.println("Client: read body bool -> " + boo);
        Float data = response.readFloat();
        System.out.println("Client: read body float -> " + data);

        client.close();
    }


}
