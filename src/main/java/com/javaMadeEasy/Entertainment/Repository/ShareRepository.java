package com.javaMadeEasy.Entertainment.Repository;

import com.javaMadeEasy.Entertainment.Entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<Share,Long> {
}
