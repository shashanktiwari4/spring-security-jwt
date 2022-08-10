package io.javafun.springsecurityjwt.Repositories;

import io.javafun.springsecurityjwt.models.BankUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<BankUser,Long> {




}
