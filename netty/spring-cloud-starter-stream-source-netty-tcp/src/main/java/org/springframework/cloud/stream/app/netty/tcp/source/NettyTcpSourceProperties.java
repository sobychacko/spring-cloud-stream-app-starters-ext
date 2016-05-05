package org.springframework.cloud.stream.app.netty.tcp.source;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.AssertTrue;
import java.util.Map;

/**
 * @author Soby Chacko
 */
@ConfigurationProperties
public class NettyTcpSourceProperties {

	private String host = "localhost";

	private int port;

	private int selectorCount = 2; //TODO - const

	private Map<String, String> lengthFieldFrameDecoder;

	private Integer maxLineLength;
	private String selectorName = "netty-tcp-selector";
	private boolean selectorDaemon = false;
	private int workerThreadCount;
	private String workerName = "netty-tcp-worker";
	private boolean workerDaemon = true;
	private boolean socketKeepAlive = false;
	private Integer socketBacklog = 100;
	private boolean reuseAddress = true;
	private Integer socketLinger = 10000;
	private Integer socketTimeout = 20000;
	private boolean tcpNoDelay = true;

	public Integer getMaxLineLength() {
		return maxLineLength;
	}

	public void setMaxLineLength(Integer maxLineLength) {
		this.maxLineLength = maxLineLength;
	}

	@AssertTrue(message = "Either length or line based frame decoders are allowed, but not both")
	public boolean ensureOnlySingleFrameDecoder() {
		return maxLineLength == null ^ lengthFieldFrameDecoder.isEmpty();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Map<String, String> getLengthFieldFrameDecoder() {
		return lengthFieldFrameDecoder;
	}

	public void setLengthFieldFrameDecoder(Map<String, String> lengthFieldFrameDecoder) {
		this.lengthFieldFrameDecoder = lengthFieldFrameDecoder;
	}

	public int getSelectorCount() {
		return selectorCount;
	}

	public void setSelectorCount(int selectorCount) {
		this.selectorCount = selectorCount;
	}


	public String getSelectorName() {
		return selectorName;
	}

	public void setSelectorName(String selectorName) {
		this.selectorName = selectorName;
	}

	public boolean isSelectorDaemon() {
		return selectorDaemon;
	}

	public void setSelectorDaemon(boolean selectorDaemon) {
		this.selectorDaemon = selectorDaemon;
	}

	public int getWorkerThreadCount() {
		return workerThreadCount;
	}

	public void setWorkerThreadCount(int workerThreadCount) {
		this.workerThreadCount = workerThreadCount;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public boolean isWorkerDaemon() {
		return workerDaemon;
	}

	public void setWorkerDaemon(boolean workerDaemon) {
		this.workerDaemon = workerDaemon;
	}

	public boolean isSocketKeepAlive() {
		return socketKeepAlive;
	}

	public void setSocketKeepAlive(boolean socketKeepAlive) {
		this.socketKeepAlive = socketKeepAlive;
	}

	public Integer getSocketBacklog() {
		return socketBacklog;
	}

	public void setSocketBacklog(Integer socketBacklog) {
		this.socketBacklog = socketBacklog;
	}

	public boolean isReuseAddress() {
		return reuseAddress;
	}

	public void setReuseAddress(boolean reuseAddress) {
		this.reuseAddress = reuseAddress;
	}

	public Integer getSocketLinger() {
		return socketLinger;
	}

	public void setSocketLinger(Integer socketLinger) {
		this.socketLinger = socketLinger;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public boolean isTcpNoDelay() {
		return tcpNoDelay;
	}

	public void setTcpNoDelay(boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
	}
}

