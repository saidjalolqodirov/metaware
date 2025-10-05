package uz.qodirov.test;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.*;
import uz.qodirov.constant.TestType;
import uz.qodirov.generic.GenericAuditingEntity;
import uz.qodirov.generic.SequenceKsuidGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tests")
@SQLDelete(sql = "update tests set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class TestsEntity extends GenericAuditingEntity<String> {
    @Id
    @GeneratedValue(generator = "test_seq", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "test_seq",
            strategy = SequenceKsuidGenerator.CLASS_NAME,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = SequenceKsuidGenerator.VALUE_PREFIX_PARAMETER, value = "tst_")
            })
    @Column(length = 40)
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private TestType type;

    @Type(type = "jsonb")
    @Column(name = "pages", columnDefinition = "jsonb")
    private PageInfo pages;

    @Column(name = "duration", nullable = false)
    private Integer duration; // minutes
}
