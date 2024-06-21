package com.main.ihatemoney.data.service;

import com.main.ihatemoney.data.entity.User;
import com.main.ihatemoney.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void sendResetLink(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiration(new Timestamp(System.currentTimeMillis() + 3600000)); // 1 hour expiration
            userRepository.save(user);

            String resetUrl = constructResetUrl(token);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n" + resetUrl);
            mailSender.send(message);
        }
    }

    private String constructResetUrl(String token) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String scheme = attr.getRequest().getScheme();
        String serverName = attr.getRequest().getServerName();
        int serverPort = attr.getRequest().getServerPort();
        String contextPath = attr.getRequest().getContextPath();

        return scheme + "://" + serverName + ":" + serverPort + contextPath + "/reset-password?token=" + token;
    }

    public boolean validateResetToken(String token) {
        User user = userRepository.findByResetToken(token);
        return user != null && user.getTokenExpiration().after(new Timestamp(System.currentTimeMillis()));
    }

    public void updatePassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user != null && user.getTokenExpiration().after(new Timestamp(System.currentTimeMillis()))) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null); // Clear the reset token after use
            user.setTokenExpiration(null);
            userRepository.save(user);
        }
    }
}
