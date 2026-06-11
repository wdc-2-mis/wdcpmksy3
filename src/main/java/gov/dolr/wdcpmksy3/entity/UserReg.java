package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


	
	@Entity
	@Table(name = "user_reg")
	public class UserReg {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true)
	    private String email;

	    private String otp;

	    private LocalDateTime otpExpiry;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

		public LocalDateTime getOtpExpiry() {
			return otpExpiry;
		}

		public void setOtpExpiry(LocalDateTime otpExpiry) {
			this.otpExpiry = otpExpiry;
		}

}
