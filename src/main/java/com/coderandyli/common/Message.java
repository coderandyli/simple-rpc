package com.coderandyli.common;

import com.coderandyli.common.utils.JsonUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.nio.charset.StandardCharsets;

/**
 *
 * <h1>消息数据结构</h1>
 * ---------------------------------------------------------------------------
 * |                                 frame                                   |
 * ---------------------------------------------------------------------------
 *          ------------------------------------------------------------------
 *          |                              message                           |
 *          ------------------------------------------------------------------
 *          -------------------------------------- ---------------------------
 *          |   message header                   | |       message body      |
 *          -------------------------------------- ---------------------------
 * length   version | opCode | streamId            operation/operationResult
 */

@Data
public abstract class Message<T extends MessageBody> {

    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }

    /**
     * 编码
     */
    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    /**
     * 解码
     */
    public void decode(ByteBuf msg) {
        int version = msg.readInt();
        long streamId = msg.readLong();
        int opCode = msg.readInt();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setVersion(version);
        messageHeader.setOpCode(opCode);
        messageHeader.setStreamId(streamId);
        this.messageHeader = messageHeader;

        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
        T body = JsonUtil.fromJson(msg.toString(StandardCharsets.UTF_8), bodyClazz);
        this.messageBody = body;
    }

}
