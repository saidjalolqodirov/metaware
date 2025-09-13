package uz.qodirov.group;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.*;
import uz.qodirov.generic.GenericAuditingEntity;
import uz.qodirov.generic.SequenceKsuidGenerator;
import uz.qodirov.user.UserEntity;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "groups")
@SQLDelete(sql = "update groups set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class GroupEntity extends GenericAuditingEntity<String> {
    @Id
    @GeneratedValue(generator = "groups_seq", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "groups_seq",
            strategy = SequenceKsuidGenerator.CLASS_NAME,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = SequenceKsuidGenerator.VALUE_PREFIX_PARAMETER, value = "gro_")
            })
    @Column(length = 40)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false, updatable = false, insertable = false)
    private UserEntity teacher;

    @Column(name = "teacher_id")
    private String teacherId;

    @ManyToMany
    @JoinTable(
            name = "group_student",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<UserEntity> students = new HashSet<>();

}
