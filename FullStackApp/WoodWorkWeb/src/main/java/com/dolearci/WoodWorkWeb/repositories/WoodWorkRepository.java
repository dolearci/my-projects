package com.dolearci.WoodWorkWeb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dolearci.WoodWorkWeb.entities.WoodWork;

@Repository
public interface WoodWorkRepository extends JpaRepository<WoodWork,Long> {
}
