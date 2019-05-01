package com.scathon.tech.rpc.service.example.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class User {
    private int id;
    private String username;
}
