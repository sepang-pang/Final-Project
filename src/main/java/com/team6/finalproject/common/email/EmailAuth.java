package com.team6.finalproject.common.email;

import com.team6.finalproject.common.redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailAuth {
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    // 메일 양식 작성,발송
    public void sendCode(String email) throws MessagingException {
        String authCode = createCode();

        // 메일 내용
        String msgOfEmail = "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 meets 입니다. </h1>";
        msgOfEmail += "<br><p>아래 코드를 입력해주세요<p><br>";
        msgOfEmail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgOfEmail += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msgOfEmail += "<div style='font-size:130%'>";
        msgOfEmail += "CODE : <strong>" + authCode + "</strong><div><br/> ";
        msgOfEmail += "</div>";

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);

        String title = "meets 인증코드";
        message.setSubject(title);

        String setFrom = "meets";
        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        mailSender.send(message);
        redisUtil.setDataExpire(email, authCode, 180); // 이메일을 key로 인증코드 저장
    }

    // 인증코드 8자리 무작위 생성
    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);

            switch (idx) {
                case 0 -> key.append((char) ((int) random.nextInt(26) + 97));
                case 1 -> key.append((char) ((int) random.nextInt(26) + 65));
                case 2 -> key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
}