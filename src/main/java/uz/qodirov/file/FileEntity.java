package uz.qodirov.file;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import uz.qodirov.generic.GenericAuditingEntity;
import uz.qodirov.generic.SequenceKsuidGenerator;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLDelete(sql = "update file set deleted = 'true' where id = ?")
@Where(clause = "deleted = 'false'")
public class FileEntity extends GenericAuditingEntity<String> {

    @Id
    @GeneratedValue(generator = "file_seq", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(
            name = "file_seq",
            strategy = SequenceKsuidGenerator.CLASS_NAME,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = SequenceKsuidGenerator.VALUE_PREFIX_PARAMETER, value = "fle_")
            })
    @Column(length = 40)
    private String id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    private String type;

    public FileEntity(String guid, String name, String extension, Long size, String type) {
        this.guid = guid;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.type = type;
    }

    public String getFileName() {
        return getGuid() + "." + getExtension();
    }

    public String getFileUrl() {
        return "/file/download/" + getFileName();
    }
}
