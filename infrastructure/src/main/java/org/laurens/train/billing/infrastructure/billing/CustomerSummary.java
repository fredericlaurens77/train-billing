package org.laurens.train.billing.infrastructure.billing;

import org.laurens.train.billing.domain.journey.Journey;
import org.laurens.train.billing.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerSummary(int customerId,
        int totalCostInCents,
        List<Trip> trips) {


     private static int totalCost(List<Journey> journeys){
          return journeys.stream().map(journey -> journey.cost().amountInCents()).reduce(Integer::sum).orElse(0);
     }

     public static CustomerSummary ofUser(User user){
          return new CustomerSummary(user.userId().id(), totalCost(user.journeys()), user.journeys().stream().map(Trip::ofJourney).collect(Collectors.toList()));
     }
}
