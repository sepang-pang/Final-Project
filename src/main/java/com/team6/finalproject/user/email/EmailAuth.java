package com.team6.finalproject.user.email;

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
        String title = "meets 인증코드";
        String content = "<br><p>아래 코드를 입력해주세요<p><br>";
        content += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        content += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        content += "<div style='font-size:130%'>";
        content += "CODE : <strong>" + authCode + "</strong><div><br/> ";
        content += "</div>";

        sendEmail(email, title, content);
        redisUtil.setDataExpire(email, authCode, 180); // 이메일을 key로 인증코드 저장
    }

    public String sendPassword(String email) throws MessagingException {
        String authCode = createCode();
        String title = "meets 임시 비밀번호";
        String content = "<div align='center' style='border:1px solid black; font-family:verdana';>";
        content += "<h3 style='color:blue;'>임시 비밀번호입니다.</h3>";
        content += "<div style='font-size:130%'>";
        content += "비밀번호 : <strong>" + authCode + "</strong><div><br/> ";
        content += "</div>";

        sendEmail(email, title, content);
        return authCode;
    }

    private void sendEmail(String email, String title, String content) throws MessagingException {
        String setFrom = "meets";

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title);

        // 메일 내용
        String msgOfEmail = "<div style='margin:20px;'>";
        msgOfEmail += "<h1> 안녕하세요 meets 입니다. </h1>";
        msgOfEmail += content;

        message.setFrom(setFrom);
        message.setText(msgOfEmail, "utf-8", "html");

        mailSender.send(message);
    }

    // 인증코드 8자리 무작위 생성
    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);

            switch (idx) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        return key.toString();
    }
}