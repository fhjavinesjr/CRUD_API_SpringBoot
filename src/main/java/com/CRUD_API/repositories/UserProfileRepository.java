package com.CRUD_API.repositories;

import com.CRUD_API.entitymodels.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
