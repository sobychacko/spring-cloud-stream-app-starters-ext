package org.springframework.cloud.stream.app.netty.tcp.source;

import io.netty.bootstrap.ServerBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;

/**
 * @author Soby Chacko
 */
@EnableBinding(Source.class)
@EnableConfigurationProperties(NettyTcpSourceProperties.class)
public class NettyTcpSource {

	@Autowired
	@Bindings(NettyTcpSource.class)
	private Source channels;

	@Autowired
	private NettyTcpSourceProperties properties;

	@Bean
	public NettyTcpCustomProtocolInboundChannelAdapter adapter(ServerBootstrap serverBootstrap) {
		NettyTcpCustomProtocolInboundChannelAdapter adapter =
				new NettyTcpCustomProtocolInboundChannelAdapter(serverBootstrap, null);
		adapter.setOutputChannel(channels.output());
		adapter.setHost(properties.getHost());
		adapter.setPort(properties.getPort());
		return null;
	}

	@Bean
	public NettyTcpServerBootstrapFactoryBean nettyTcpServerBootstrapFactoryBean() {
		return new NettyTcpServerBootstrapFactoryBean(properties);
	}

}

