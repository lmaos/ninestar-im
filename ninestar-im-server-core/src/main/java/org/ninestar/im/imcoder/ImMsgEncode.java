package org.ninestar.im.imcoder;

import org.ninestar.im.msgcoder.MsgPackage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ImMsgEncode extends MessageToByteEncoder<MsgPackage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgPackage msg, ByteBuf out) throws Exception {

		byte[] topBytes = msg.getTopBytes();
		byte[] headBytes = msg.getHeadBytes();
		byte[] bodyBytes = msg.getBodyBytes();
		out.writeBytes(topBytes);
		out.writeBytes(headBytes);
		out.writeBytes(bodyBytes);
		
	}

}
