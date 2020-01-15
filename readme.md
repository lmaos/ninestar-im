version:1.0
启动项目时间: 2019-08-22
目前进度: 正在研发

项目说明：依赖于Spring-boot + netty-tcp 的服务器端项目，启动项目需要依赖于spring

2019-05-25:

    服务器基础功能已经开发完毕
    客户端基础功能已经开发完毕
2020-01-14
    
    增加 Server 集群功能。
    集群使用 @EnableNineStarZkRegister 启用
    增加host绑定，serverID，clientId。
    增加消息转发，分发。
2020-01-15
	
	增加客户端订阅功能
	
    
目前进度: 持续研发中


服务器启动 

	@SpringBootApplication
	@EnableNineStarImServer(port=12345)
	public class ServerStartDemo {
		public static void main(String[] args) {
			SpringApplication.run(ServerStartDemo.class, args);
		}
	}

创建控制处理

	@NineStarSerUri("/user")
	public class UserTcpController {
		Map<String, String> signupMap = new ConcurrentHashMap<String, String>();
		@NineStarSerUri("/signup")
		public void signup(
				@RequestParam("username") String username, 
				@RequestParam("password") String password,
				NineStarImMsgSerV0Response response) {
			String key = username;
			if (signupMap.containsKey(key)) {
				response.setState(10);
				response.setMsg("已经存在用户名");
			} else {
				signupMap.put(username, password);
				response.setMsg("注册成功");
			}
	
		}
	}

客户端的使用:


        NineStarImClient c = new NineStarImClient("localhost", 12345);
		NineStarImV0Output out = c.getNineStarImV0Output();
		// 注册的请求
		NineStarImMsgCliV0Request signupReq = new NineStarImMsgCliV0Request("/user/signup");
		signupReq.setHeaderValue("username", "ninestar");
		signupReq.setHeaderValue("password", "123456");
		// 注册
		NineStarImMsgCliV0Response signupResp = out.sendSync(signupReq);
		

服务器启动2 （集群）

	@SpringBootApplication
	@EnableNineStarImServer(port=12345, host="localhost")
	@EnableNineStarZkRegister(registerConnecton="127.0.0.1:2181")
	public class ServerStartDemo {
		public static void main(String[] args) {
			SpringApplication.run(ServerStartDemo.class, args);
		}
	}
	
服务器内获取其他服务器连接客户实例

	@Autowired
	NineStarNameser nameser; // 在服务器内使用
	
	{
		// 获取访问serverId的服务器连接客户端
		NineStarImClient client = nameser.getNineStarImClient(serverId); 
		
		// 调用 nameser.send方法 将会自动识别targerIds所在服务端，进行转发。
		nameser.send(targerIds[], response);// 转发分发，targerIds 是客户端连接服务器的唯一标识，response 发送给这些客户终端的内容
		
	}
	
服务器推送消息

	@Autowired
	NineStarImServer server; // 在服务器内使用
	
	{
		server.send(response); // 将消息推送给所有客户端
		server.send(targerId, response); // 将消息推送给 targerId
		server.send(targerIds[], response); // 将消息推送给 这些客户端
		
	}
	


客户端订阅的功能
	
	// 服务器端推送到uri = /subscribe/ok 的消息将会进入 UriSubscribe的实现进行处理
	
	NineStarImClient c = new NineStarImClient("localhost", 12345);
	NineStarSubscribe nineStarSubscribe = c.getNineStarSubscribe();
	// 设置订阅线程池【可选】
	// nineStarSubscribe.setThreadPool(Executors.newFixedThreadPool(3, Named.newThreadFactory("nineStarSubscribe")));
		
	// 增加订阅服务器的推送消息
	c.getNineStarSubscribe().addSubscribe("/subscribe/ok", new UriSubscribe() {
			
		@Override
		public void onmessage(NineStarImCliResponse response) throws Exception {
			System.out.println("订阅："+response.bodyToString()); 
		}
		
	});
		
	//	c.close(); 不需要关闭
		
		