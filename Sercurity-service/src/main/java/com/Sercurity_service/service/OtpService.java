package com.Sercurity_service.service;

import com.Sercurity_service.dto.request.ChangePasswordRequest;
import com.Sercurity_service.dto.response.OtpResponse;
import com.Sercurity_service.entity.Otp;
import com.Sercurity_service.entity.Users;
import com.Sercurity_service.exception.AppException;
import com.Sercurity_service.exception.ErrorCode;
import com.Sercurity_service.repository.OtpRepository;
import com.Sercurity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class OtpService {
    @Autowired
    OtpRepository otpRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailSender mailSender;

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
        sendOtpToMail(otpValue, email);
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
}
