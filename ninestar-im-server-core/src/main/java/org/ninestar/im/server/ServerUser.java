package org.ninestar.im.server;

import org.ninestar.im.msgcoder.MsgPackage;

import io.netty.channel.ChannelHandlerContext;

public class ServerUser {

	private String clientId;
	private String serverId;
	private NineStarImSerHandler nsih;

	public ServerUser(ChannelHandlerContext ctx, NineStarImSerHandler nsih) {
		this.nsih = nsih;
		this.clientId = nsih.getBox().getBoxId();
		this.serverId = nsih.getNineStarImServer().getServerId();
	}

	public void send(NineStarImSerResponse response) {
		MsgPackage msgPackage = response.toMsgPackage();
		nsih.writeAndFlush(msgPackage);
	}

	public String getClientId() {
		return clientId;
	}

	public String getServerId() {
		return serverId;
	}

	public NineStarImSerHandler getNsih() {
		return nsih;
	}

}
