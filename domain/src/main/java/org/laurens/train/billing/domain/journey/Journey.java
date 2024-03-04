package org.laurens.train.billing.domain.journey;

import org.laurens.train.billing.domain.user.User;

public record Journey(User user, Tap start, Tap end) {

}
