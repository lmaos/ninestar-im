package org.ninestar.im.server;

import java.nio.charset.Charset;

import org.ninestar.im.msgcoder.BodyInput;

public interface NineStarImSerRequest extends BodyInput{

	NineStarImSerHead getHead();

	byte[] getBody();

	default String bodyToString(String charsetName) {
		return new String(getBody(), Charset.forName(charsetName));
	}

	default String bodyToString() {
		return new String(getBody(), Charset.forName(getHead().getCharsetName()));
	}

	void resetInput();

	void resetReadIndex();

	void markReadIndex();
}
