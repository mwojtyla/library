package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CopyDto {
    private Long id;
    private String status;
    private Long titleId;
}
