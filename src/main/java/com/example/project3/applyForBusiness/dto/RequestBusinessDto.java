package com.example.project3.applyForBusiness.dto;

import com.example.project3.applyForBusiness.RequestStatus;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import lombok.Getter;

@Getter
public class RequestBusinessDto {
    RequestStatus status;
    String reason;

    public static RequestBusinessDto fromEntity(RequestBusinessEntity entity){
        RequestBusinessDto dto = new RequestBusinessDto();
        dto.status = entity.getStatus();
        dto.reason = entity.getReason();
        return dto;
    }
}
