package com.mangh.taskrit.repository;

import com.mangh.taskrit.model.BoardRole;
import com.mangh.taskrit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRoleRepository extends JpaRepository<BoardRole, UUID> {

    @Override
    <S extends BoardRole> S save(S s);

    List<BoardRole> findAllByUser(User user);
}
