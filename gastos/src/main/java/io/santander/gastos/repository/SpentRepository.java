package io.santander.gastos.repository;

import io.santander.gastos.domain.Spent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentRepository extends JpaRepository<Spent, Long> {
    
}
