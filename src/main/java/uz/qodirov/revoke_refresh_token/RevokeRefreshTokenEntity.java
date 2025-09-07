package uz.qodirov.revoke_refresh_token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.generic.GenericEntity;

import javax.persistence.*;

@Entity
@Table(name = "revoke_refresh_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class RevokeRefreshTokenEntity implements GenericEntity<String> {

    @Id
    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @CreatedDate
    @Column(name = "created_date")
    private Long createdDate;


    @Column(name = "user_id")
    private String userId;

    public RevokeRefreshTokenEntity(String refreshToken, String userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    @Override
    public String getId() {
        return refreshToken;
    }

    @Override
    public boolean isDeleted() {
        return false;
    }
}
