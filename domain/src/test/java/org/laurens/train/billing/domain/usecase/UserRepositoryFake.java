package org.laurens.train.billing.domain.usecase;

import org.laurens.train.billing.domain.user.User;
import org.laurens.train.billing.domain.user.UserId;
import org.laurens.train.billing.domain.user.UserNotInTransit;
import org.laurens.train.billing.domain.user.UserRepository;

import java.util.HashMap;
import java.util.List;

public class UserRepositoryFake implements UserRepository {

    private final HashMap<UserId, User> users = new HashMap<>();
    @Override
    public User findOrCreateUser(UserId userId) {
        if(!this.users.containsKey(userId)){
            User newUser = new UserNotInTransit(userId, User.NO_JOURNEY);
            this.users.put(userId, newUser);
            return newUser;
        } else {
            return this.users.get(userId);
        }
    }

    @Override
    public void updateUser(UserId userId, User user) {
        this.users.put(userId, user);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

}
