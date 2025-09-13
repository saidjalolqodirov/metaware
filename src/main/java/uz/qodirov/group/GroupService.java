package uz.qodirov.group;

import uz.qodirov.generic.JpaGenericService;

public interface GroupService extends JpaGenericService<GroupEntity, String> {
    GroupEntity create(GroupRequest request);

    GroupEntity update(String id, GroupRequest request);

    GroupEntity getOne(String id);

    GroupEntity attachStudent(GroupStudentRequest request);

    GroupEntity detachStudent(GroupStudentRequest request);
}
