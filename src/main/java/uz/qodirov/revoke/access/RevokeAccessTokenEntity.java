package uz.qodirov.revoke.access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.generic.GenericEntity;

import javax.persistence.*;

@Entity
@Table(name = "revoke_access_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class RevokeAccessTokenEntity implements GenericEntity<String> {

    @Id
    @Column(name = "access_token", unique = true)
    private String accessToken;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private Long createdDate;


    @Column(name = "user_id")
    private String userId;

    public RevokeAccessTokenEntity(String accessToken, String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    @Override
    public String getId() {
        return accessToken;
    }

    @Override
    public boolean isDeleted() {
        return false;
    }
}
