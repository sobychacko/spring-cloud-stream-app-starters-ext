package org.springframework.cloud.stream.app.netty.tcp.source;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

/**
 * @author Soby Chacko
 */
public class NettyTcpServerBootstrapFactoryBean implements FactoryBean<ServerBootstrap> {

	private transient ServerBootstrap server;

	private transient NioEventLoopGroup selectorGroup;
	private transient NioEventLoopGroup workerGroup;

	private final NettyTcpSourceProperties sourceProperties;

	public NettyTcpServerBootstrapFactoryBean(NettyTcpSourceProperties sourceProperties) {
		this.sourceProperties = sourceProperties;
	}

	@Override
	public ServerBootstrap getObject() throws Exception {
		return server;
	}

	@Override
	public Class<?> getObjectType() {
		return ServerBootstrap.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@PostConstruct
	private void initialize() throws Exception {
		this.selectorGroup = new NioEventLoopGroup(sourceProperties.getSelectorCount(),
				new NettyServerThreadFactory(sourceProperties.getSelectorName(),
						sourceProperties.isSelectorDaemon()));
		this.workerGroup = new NioEventLoopGroup(sourceProperties.getWorkerThreadCount(),
				new NettyServerThreadFactory(sourceProperties.getWorkerName(),
						sourceProperties.isWorkerDaemon()));

		this.server = new ServerBootstrap().group(selectorGroup, workerGroup)
				.option(ChannelOption.SO_KEEPALIVE, sourceProperties.isSocketKeepAlive())
				.option(ChannelOption.SO_BACKLOG, sourceProperties.getSocketBacklog())
				.option(ChannelOption.SO_REUSEADDR, sourceProperties.isReuseAddress())
				.option(ChannelOption.SO_LINGER, sourceProperties.getSocketLinger())
				.option(ChannelOption.SO_TIMEOUT, sourceProperties.getSocketTimeout())
				.option(ChannelOption.TCP_NODELAY, sourceProperties.isTcpNoDelay())
				.option(ChannelOption.ALLOCATOR, new UnpooledByteBufAllocator(false))
				.childOption(ChannelOption.SO_KEEPALIVE,
						sourceProperties.isSocketKeepAlive())
				.childOption(ChannelOption.SO_REUSEADDR,
						sourceProperties.isReuseAddress())
				.childOption(ChannelOption.SO_LINGER, sourceProperties.getSocketLinger())
				.childOption(ChannelOption.TCP_NODELAY, sourceProperties.isTcpNoDelay());
	}

	private static class NettyServerThreadFactory implements ThreadFactory {

		private final String name;
		private final boolean daemon;

		public NettyServerThreadFactory(String name, boolean daemon) {
			this.name = name;
			this.daemon = daemon;
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r, name + UUID.randomUUID());
			thread.setDaemon(daemon);
			return thread;
		}
	}
}
