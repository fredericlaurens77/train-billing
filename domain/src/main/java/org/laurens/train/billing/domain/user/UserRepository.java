package org.laurens.train.billing.domain.user;

import java.util.List;

public interface UserRepository {

    User findOrCreateUser(UserId userId);

    void updateUser(UserId userId, User user);

    List<User> getAll();
}
