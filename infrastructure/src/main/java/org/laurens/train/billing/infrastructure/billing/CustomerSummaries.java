package org.laurens.train.billing.infrastructure.billing;

import org.laurens.train.billing.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerSummaries(List<CustomerSummary> customerSummaries) {

    public static CustomerSummaries ofUserList(List<User> users){
        return new CustomerSummaries(users.stream().map(CustomerSummary::ofUser).collect(Collectors.toList()));
    }
}
