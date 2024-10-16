package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.dto.RequestBusinessDto;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("businessRequest")
public class BusinessController {
    private final BusinessService service;

    @PostMapping("/toBusinessRequest")
    public RequestBusinessDto createRequest(
            @RequestBody
            RequestBusinessDto dto
    ) {
      return service.toBusinessRequest(dto);

    }
}
