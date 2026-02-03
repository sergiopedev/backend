package com.motogarage.backend.repository;

import com.motogarage.backend.model.Mod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModRepository extends JpaRepository<Mod, Long> {
}