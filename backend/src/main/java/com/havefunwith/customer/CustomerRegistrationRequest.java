package com.havefunwith.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
