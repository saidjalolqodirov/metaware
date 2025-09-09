package uz.qodirov.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.qodirov.constant.Role;
import uz.qodirov.constant.Status;
import uz.qodirov.user.UserEntity;
import uz.qodirov.user.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            System.out.println("No users found");
            UserEntity user = new UserEntity();
            user.setUsername("admin");
            user.setPassword("$2a$10$OkEdZwDCo2cpxhINE6aSXeUQ7kVpTS9.zYOqcWpGRPURyj7Wh1LSe"); // 123456
            user.setStatus(Status.ACTIVE);
            user.setRole(Role.ADMIN);
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setEmail("admin@gmail.com");
            userRepository.save(user);
        }
    }
}
