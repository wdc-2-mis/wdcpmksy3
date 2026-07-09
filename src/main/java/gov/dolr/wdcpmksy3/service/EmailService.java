package gov.dolr.wdcpmksy3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("WDC-PMKSY 3.0 | One-Time Password (OTP)");

        message.setText(

        "Dear User,\n\n"

        + "Your One-Time Password (OTP) for WDC-PMKSY 3.0 login is:\n\n"

        + otp +

        "\n\n"

        + "This OTP is valid for 30 minutes."

        + "\n\n"

        + "Do not share this OTP with anyone."

        + "\n\n"

        + "Regards,"

        + "\nWDC-PMKSY 3.0"

        + "\nDepartment of Land Resources"

        + "\nGovernment of India"

        );

        mailSender.send(message);
    }
}