package com.example.r2dbch2.dao;

import com.example.r2dbch2.entity.UserDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveCrudRepository<UserDO, Long> {
    Mono<UserDO> findByEmail(String email);
}
