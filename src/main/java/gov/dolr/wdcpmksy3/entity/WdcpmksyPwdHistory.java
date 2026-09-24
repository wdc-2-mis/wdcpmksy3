package gov.dolr.wdcpmksy3.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "wdcpmksy_pwd_history", schema = "public")
public class WdcpmksyPwdHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reg_id", nullable = false)
    private Integer regId;

    @Column(name = "encrypted_password", nullable = false, length = 200)
    private String encryptedPassword;

    @Column(name = "changed_date", nullable = false)
    private LocalDateTime changedDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public LocalDateTime getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(LocalDateTime changedDate) {
        this.changedDate = changedDate;
    }
}