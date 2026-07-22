package com.example.project.repositories;

import com.example.project.model.Restaurents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurentRepository extends JpaRepository<Restaurents, Long> {


}
