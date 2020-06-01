package ml.socshared.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ml.socshared.gateway.domain.TestObject;

@Repository
public interface TestRepository extends JpaRepository<TestObject, Integer> {
}
