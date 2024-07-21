package com.example.jpashop.repository;

import com.example.jpashop.domain.Team;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TeamJpaRepository {

    private EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }
}
