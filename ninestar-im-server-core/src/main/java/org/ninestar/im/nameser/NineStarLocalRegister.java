package org.ninestar.im.nameser;

import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.server.NineStarImSerResponse;
import org.ninestar.im.server.NineStarImServer;
import org.ninestar.im.server.anns.NineStarServerRegister;
import org.ninestar.im.server.config.EnableNineStarImServer;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;
@Conditional(NineStarLocalRegister.class)
@Component
public class NineStarLocalRegister implements NineStarNameser, Condition {
	private NineStarImServer server;

	@Override
	public void register(NineStarImServer server) {
		this.server = server;
	}

	@Override
	public NineStarImClient getNineStarImClient(String serverId) {

		return null;
	}

	@Override
	public void send(String[] targerIds, NineStarImSerResponse response) {
		server.send(targerIds, response);

	}

	@Override
	public void send(NineStarImSerResponse response) {
		server.send(response);
	}

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String[] startAnns = context.getBeanFactory().getBeanNamesForAnnotation(NineStarServerRegister.class);
		
		return startAnns == null || startAnns.length == 0;
	}

}
