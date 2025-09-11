package uz.qodirov.user;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.*;
import uz.qodirov.constant.Role;
import uz.qodirov.constant.Status;
import uz.qodirov.file.FileEntity;
import uz.qodirov.generic.GenericAuditingEntity;
import uz.qodirov.generic.SequenceKsuidGenerator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@SQLDelete(sql = "update users set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class UserEntity extends GenericAuditingEntity<String> {
    @Id
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "user_seq",
            strategy = SequenceKsuidGenerator.CLASS_NAME,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = SequenceKsuidGenerator.VALUE_PREFIX_PARAMETER, value = "use_")
            })
    @Column(length = 40)
    private String id;

    @Column(length = 40, nullable = false, name = "username")
    private String username;

    @Column(length = 40, name = "first_name")
    private String firstName;

    @Column(length = 40, name = "last_name")
    private String lastName;

    @Column(length = 40, name = "middle_name")
    private String middleName;

    @Column(length = 40, name = "phone")
    private String phone;

    @Column(length = 40, nullable = false, name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private FileEntity image;

    @Column(name = "image_id", insertable = false, updatable = false)
    private String imageId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    @Type(type = "jsonb")
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private HashMap<String, Object> additionalInfo;
}
