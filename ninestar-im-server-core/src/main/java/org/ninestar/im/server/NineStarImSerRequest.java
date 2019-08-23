package org.ninestar.im.server;

public interface NineStarImSerRequest {
	
	NineStarImSerHead getHead();
	
	byte[] getBody();

}
