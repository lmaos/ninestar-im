package org.ninestar.im.imcoder;

import java.util.List;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.msgcoder.MsgUtils;
import org.ninestar.im.msgcoder.exception.VersionErrorException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ImMsgDecode extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			MsgPackage msg = MsgUtils.readMsgPackage((short) 0, in);
			if (msg != null) {
				out.add(msg);
			}
		} catch (VersionErrorException e) {
			ctx.close();
		}
	}

}
