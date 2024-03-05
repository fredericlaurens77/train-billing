package org.laurens.train.billing.domain.user;

public record UserId(int id){
    public static UserId of(int id){
        return new UserId(id);
    }
}
