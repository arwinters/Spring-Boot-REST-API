package com.example.repository;

import com.example.model.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server,Long>{
    @Override
    Optional<Server> findById(@Param("id") Long aLong);

    @RestResource(path="searchByName", rel="searchByName")
    Optional<Server> findByName(@Param("name") String name);

    Page<Server> findById(@Param("id") Long aLong, Pageable pageable);
}
