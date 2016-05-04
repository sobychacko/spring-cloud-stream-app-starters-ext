package org.springframework.cloud.stream.app.netty.tcp.source;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.AssertTrue;
import java.util.Map;

/**
 * @author Soby Chacko
 */
@ConfigurationProperties
public class NettyTcpSourceProperties {

	private Map<String, String> lengthFieldFrameDecoder;

	private Integer maxLineLength;

	public Map<String, String> getLengthFieldFrameDecoder() {
		return lengthFieldFrameDecoder;
	}

	public void setLengthFiledFrameDecoder(Map<String, String> lengthFieldFrameDecoder) {
		this.lengthFieldFrameDecoder = lengthFieldFrameDecoder;
	}

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
}

