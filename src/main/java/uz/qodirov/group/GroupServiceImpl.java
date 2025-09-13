package uz.qodirov.group;

import org.springframework.stereotype.Service;
import uz.qodirov.constant.Role;
import uz.qodirov.exception.BadRequestException;
import uz.qodirov.generic.JpaGenericRepository;
import uz.qodirov.generic.JpaGenericServiceImpl;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserService;
import uz.qodirov.util.SecurityContextUtil;

@Service
public class GroupServiceImpl extends JpaGenericServiceImpl<GroupEntity, String> implements GroupService {
    private final GroupRepository repository;
    private final UserService userService;

    public GroupServiceImpl(GroupRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    protected JpaGenericRepository<GroupEntity, String> getRepository() {
        return this.repository;
    }

    @Override
    public GroupEntity create(GroupRequest request) {
        UserEntity user = SecurityContextUtil.getUser().getUser();
        if (user == null || user.getRole() != Role.TEACHER) {
            throw new BadRequestException("Only teacher can create group");
        }
        GroupEntity group = new GroupEntity();
        group.setName(request.getName());
        group.setTeacher(user);
        group.setTeacherId(user.getId());
        return save(group);
    }

    @Override
    public GroupEntity update(String id, GroupRequest request) {
        GroupEntity group = findById(id);
        group.setName(request.getName());
        return save(group);
    }

    @Override
    public GroupEntity getOne(String id) {
        return findById(id);
    }

    @Override
    public GroupEntity attachStudent(GroupStudentRequest request) {
        GroupEntity group = findById(request.getGroupId());
        UserEntity student = userService.findById(request.getStudentId());
        group.getStudents().add(student);
        return save(group);
    }

    @Override
    public GroupEntity detachStudent(GroupStudentRequest request) {
        GroupEntity group = findById(request.getGroupId());
        UserEntity student = userService.findById(request.getStudentId());
        group.getStudents().remove(student);
        return save(group);
    }
}
