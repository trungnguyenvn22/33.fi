package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.ChangePasswordRequest;
import com.Sercurity_service.dto.response.OtpResponse;
import com.Sercurity_service.entity.Otp;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.repository.OtpRepository;
import com.Sercurity_service.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OtpService {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    String otpValue;
    String email;
    LocalDateTime expTime;
    boolean used;
    public OtpResponse generateOtp(String email){
        Otp otp = new Otp();
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        otpRepository.deleteByEmail(email);
        String otpValue = generateRandomOtp();
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5);
        otp.setOtpValue(otpValue);
        otp.setEmail(email);
        otp.setExpTime(expiredTime);
        otp.setUsed(false);
        otpRepository.save(otp);
       // sendOtpToMail(otpValue, email);
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("userName", user.getFullName());
        templateModel.put("imageResourceName", "logo");
        templateModel.put("otpCode", otpValue);
        sendOtpWithTemplate(email, templateModel);
        return new OtpResponse("OTP send successfully", true);
    }

    public OtpResponse changePassword(ChangePasswordRequest request){
        Otp otp = otpRepository.findByEmailAndOtpValueAndExpTimeFalse(request.getEmail(), request.getOtpValue())
                .orElseThrow(()-> new AppException(ErrorCode.OTP_NOT_FOUND));
        if(otp.isExpired())
            throw new AppException(ErrorCode.OTP_HAS_EXPIRED);
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        otp.setUsed(true);
        return new OtpResponse("Change password Successful!", true);
    }

    String generateRandomOtp(){
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendOtpToMail(String otp, String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("33.fi- THÔNG TIN OTP");
        message.setText("Chúng tôi gửi đến bạn mã OTP dùng để xác nhận thay đổi MẬT KHẨU trên hệ thống 33.fi.");
        message.setText("Your OTP for password reset is: " + otp +
                "\nThis OTP will expire in 5 minutes.");
        mailSender.send(message);

    }

    void sendOtpWithTemplate( String email, Map<String,Object> templateModel){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Context thymeleafContext = new Context();
            thymeleafContext.setVariables(templateModel);

            String htmlBody = templateEngine.process("email-template", thymeleafContext);

            helper.setTo(email);
            helper.setSubject("YÊU CẦU THAY ĐỔI MẬT KHẨU");
            helper.setText(htmlBody, true);

            // Thêm hình ảnh
            ClassPathResource imageResource = new ClassPathResource("static/image/logo.png");
            helper.addInline("imageResourceName", imageResource);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }


}
