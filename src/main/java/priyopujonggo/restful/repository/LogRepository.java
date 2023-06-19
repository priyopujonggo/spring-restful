package priyopujonggo.restful.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import priyopujonggo.restful.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    Log findByEmail(String email);
}
