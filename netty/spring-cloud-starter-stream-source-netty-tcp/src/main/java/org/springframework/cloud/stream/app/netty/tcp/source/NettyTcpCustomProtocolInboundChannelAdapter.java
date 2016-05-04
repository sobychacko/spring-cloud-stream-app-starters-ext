package org.springframework.cloud.stream.app.netty.tcp.source;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.util.Assert;

import java.nio.ByteBuffer;

/**
 * Netty Inbound channel adapter for receiving TCP messages for custom protocols.
 *
 * @author Soby Chacko
 */
public class NettyTcpCustomProtocolInboundChannelAdapter extends MessageProducerSupport {

	private final ServerBootstrap serverBootstrap;
	private final ChannelInboundHandler inboundHandler;

	public NettyTcpCustomProtocolInboundChannelAdapter(ServerBootstrap serverBootstrap,
													   ChannelInboundHandler inboundHandler) {
		this.serverBootstrap = serverBootstrap;
		this.inboundHandler = inboundHandler;
	}

	@Override
	protected void onInit() {
		super.onInit();
		Assert.notNull(serverBootstrap, "server bootstrap cannot be null");

		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(final SocketChannel socketChannel) {
				final ChannelPipeline pipeline = socketChannel.pipeline();
				socketChannel.config().setAllocator(new UnpooledByteBufAllocator(false));

				pipeline.addFirst("frameDecoder", inboundHandler);
				pipeline.addLast("dataDecoder", new MessageProducingHandler());

			}
		});

	}

	class MessageProducingHandler extends SimpleChannelInboundHandler<ByteBuf> {

		@Override
		protected void channelRead0(ChannelHandlerContext context, ByteBuf buffer) {
			int readableBytes = buffer.readableBytes();

			ByteBuffer byteBuffer = buffer.nioBuffer(0, readableBytes);

		}
	}
}

