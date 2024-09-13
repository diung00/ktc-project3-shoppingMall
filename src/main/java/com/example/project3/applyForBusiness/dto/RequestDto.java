package com.example.project3.applyForBusiness.dto;

import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDto {

    private String reason;

    public static RequestDto fromEntity(RequestBusinessEntity entity){
        RequestDto requestDto = new RequestDto();
        requestDto.setReason(entity.getReason());
        return requestDto;
    }
}
