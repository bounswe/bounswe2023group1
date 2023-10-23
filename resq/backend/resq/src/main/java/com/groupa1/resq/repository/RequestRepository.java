package main.java.com.groupa1.resq.repository;

import com.groupa1.resq.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findById(Long requestId);

    Boolean existsById(Long requestId);

}
