package pt.joja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.joja.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
