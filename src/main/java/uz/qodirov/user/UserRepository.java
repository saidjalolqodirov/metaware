package uz.qodirov.user;


import uz.qodirov.generic.JpaGenericRepository;

import java.util.Optional;

public interface UserRepository extends JpaGenericRepository<UserEntity, String> {
    Optional<UserEntity> findFirstByUsername(String username);
}
