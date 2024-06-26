package com.recycleBusiness.RecyclePal.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Sender {
    private final String name;
    private final String email;
}
