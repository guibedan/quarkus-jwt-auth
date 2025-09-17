package com.guibedan.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "tb_password_reset")
public class PasswordResetToken extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public UUID token;

    @ManyToOne
    public User user;

    public Instant expiryDate;

    public static Optional<PasswordResetToken> findByTokenAndAfterDateNowOptional(UUID uuid, Instant now) {
        return find("token = ?1 and expiryDate > ?2 and used = false", uuid, now).singleResultOptional();
    }

}
