package com.fortuna.bampo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * 编码解码工具类
 *
 * @author Eva7
 * @since 0.1.1
 */
@Configuration
public class CodecUtil {

    public static String encodeUuid(UUID uuid) {
        byte[] encoded = Base64.getUrlEncoder().withoutPadding().encode(uuidToBytes(uuid));
        return new String(encoded, 0, encoded.length);
    }

    public static UUID decodeUuid(String encodedStr) {
        byte[] decoded = Base64.getUrlDecoder().decode(encodedStr.getBytes());
        return uuidFromBytes(decoded);
    }

    private static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    private static UUID uuidFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
