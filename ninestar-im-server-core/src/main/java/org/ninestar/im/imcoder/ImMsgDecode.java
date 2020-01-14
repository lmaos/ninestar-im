package org.ninestar.im.imcoder;

import java.util.List;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.msgcoder.MsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 消息解码
 * 
 * @author zhangxingyu
 *
 */
public class ImMsgDecode extends ByteToMessageDecoder {

	private static final Logger log = LoggerFactory.getLogger(ImMsgDecode.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			Short version = MsgUtils.readVersion(in);
			if (version == null) {
				return;
			}
			if (version == 0 || version == 1) { // 支持 0 , 1
				MsgPackage msg = MsgUtils.readMsgPackage(version, in);
				if (msg != null) {
					out.add(msg);
				}
			} else {
				log.error("解析的版本号错误:" + version);
				ctx.close();
			}
		} catch (Exception e) {

			ctx.close();
		}
	}

}
