package uz.qodirov.user;

import lombok.*;
import org.hibernate.annotations.*;
import uz.qodirov.constant.Privilege;
import uz.qodirov.constant.Role;
import uz.qodirov.constant.Status;
import uz.qodirov.file.FileEntity;
import uz.qodirov.generic.GenericAuditingEntity;
import uz.qodirov.generic.SequenceKsuidGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@SQLDelete(sql = "update users set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(length = 40, name = "phone")
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private FileEntity image;

    @Column(name = "image_id", insertable = false, updatable = false)
    private String imageId;

    @ElementCollection(targetClass = Privilege.class)
    @CollectionTable(name = "user_privilege")
    @Column(name = "privilege", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<Privilege> privileges;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    public void addPrivilege(Privilege privilege) {
        if (this.privileges == null) {
            this.privileges = new ArrayList<>();
        }
        this.privileges.add(privilege);
    }
}
