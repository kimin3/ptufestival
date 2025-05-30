package com.capstone7.ptufestival.booth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BoothDetailResponseDto {

    private int id;
    private String name;
    private String department;
    private String theme;
    private String concept;
    private String imageUrl;
    private String location;
    private String time;
    private List<ServiceDto> services;
}
