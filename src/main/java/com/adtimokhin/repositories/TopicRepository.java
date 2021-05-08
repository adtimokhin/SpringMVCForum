package com.adtimokhin.repositories;

import com.adtimokhin.models.Roles;
import com.adtimokhin.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 14.04.2021
 **/

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> getAllByUser_RolesIsContaining(Set<Roles> roles);
}
