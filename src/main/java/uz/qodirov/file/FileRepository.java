package uz.qodirov.file;

import org.springframework.stereotype.Repository;
import uz.qodirov.generic.JpaGenericRepository;

@Repository
public interface FileRepository extends JpaGenericRepository<FileEntity, String> {
    FileEntity findByGuidAndDeletedFalse(String guid);

}
