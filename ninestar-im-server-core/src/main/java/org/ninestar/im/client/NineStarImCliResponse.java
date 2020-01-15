package org.ninestar.im.client;

import java.nio.charset.Charset;

import org.ninestar.im.msgcoder.BodyInput;

public interface NineStarImCliResponse extends BodyInput{

	long getMsgPackId();

	NineStarImCliHead getHead();

	byte[] getBody();

	int getState(); // 获得应答状态
	
	default String bodyToString(String charsetName) {
		return new String(getBody(), Charset.forName(charsetName));
	}

	default String bodyToString() {
		return new String(getBody(), Charset.forName(getHead().getCharsetName()));
	}
}
