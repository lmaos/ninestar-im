package org.ninestar.im.server;

import org.ninestar.im.msgcoder.MsgPackage;

import io.netty.channel.ChannelHandlerContext;
/**
 * 消息处理
 */
public interface NineStarImSerVxHandler {
	public void handler(MsgPackage msg, ChannelHandlerContext ctx, NineStarImSerHandler nsih) throws Exception ;
}
