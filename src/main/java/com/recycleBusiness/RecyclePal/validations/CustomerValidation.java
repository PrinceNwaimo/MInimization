package com.recycleBusiness.RecyclePal.validations;

import com.recycleBusiness.RecyclePal.data.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerValidation {
    private final CustomerRepository customerRepository;
}
