package org.ninestar.im.server;

import org.ninestar.im.msgcoder.MsgPackage;

import io.netty.channel.ChannelHandlerContext;

public interface NineStarImSerVxHandler {
	public void handler(MsgPackage msg, ChannelHandlerContext ctx, NineStarImSerHandler nsih) throws Exception ;
}
