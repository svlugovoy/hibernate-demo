package com.svlugovoy.hibernatedemo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieProjection {
    private Long id;
    private String name;

}
