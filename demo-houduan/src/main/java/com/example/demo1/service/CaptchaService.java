package com.example.demo1.service;

import com.example.demo1.exception.BusinessException;
import com.example.demo1.response.ResponseCode;
import com.example.demo1.util.CaptchaUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaService {

    private static final long EXPIRE_MS = 5 * 60 * 1000L;

    private final Map<String, CaptchaEntry> captchaStore = new ConcurrentHashMap<>();

    public Map<String, String> generateCaptcha() throws IOException {
        String code = CaptchaUtil.generateCode();
        String key = UUID.randomUUID().toString().replace("-", "");
        captchaStore.put(key, new CaptchaEntry(code.toLowerCase(), System.currentTimeMillis()));
        cleanExpired();

        return Map.of(
                "captchaKey", key,
                "captchaImage", CaptchaUtil.generateImageBase64(code)
        );
    }

    public void validate(String key, String code) {
        if (key == null || code == null || code.isBlank()) {
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        }
        CaptchaEntry entry = captchaStore.remove(key);
        if (entry == null || System.currentTimeMillis() - entry.createTime > EXPIRE_MS) {
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        }
        if (!entry.code.equalsIgnoreCase(code.trim())) {
            throw new BusinessException(ResponseCode.CAPTCHA_ERROR);
        }
    }

    private void cleanExpired() {
        long now = System.currentTimeMillis();
        captchaStore.entrySet().removeIf(e -> now - e.getValue().createTime > EXPIRE_MS);
    }

    private record CaptchaEntry(String code, long createTime) {}
}
