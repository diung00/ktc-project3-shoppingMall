package com.example.project3.applyForBusiness.dto;

import com.example.project3.applyForBusiness.entity.RequestEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDto {


    private String username;
    private String reason;

    public static RequestDto fromEntity(RequestEntity entity){
        RequestDto requestDto = new RequestDto();
        requestDto.setUsername(entity.getUser().getUsername());
        requestDto.setReason(entity.getReason());
        return requestDto;
    }
}
