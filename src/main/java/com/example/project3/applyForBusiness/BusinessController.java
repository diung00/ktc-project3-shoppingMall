package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.dto.RequestDto;
import com.example.project3.applyForBusiness.dto.ResponseDto;
import com.example.project3.applyForBusiness.entity.RequestEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("business-requests")
public class BusinessController {
    private final BusinessService service;
        public BusinessController(BusinessService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public String createRequest(
            @PathVariable("userId")
            Long userId,
            @RequestBody
            RequestDto dto
    ) {
        RequestEntity request = service.createRequest(userId,dto);
        return "Your request has been sent";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/view-requests")
    public List<RequestEntity> viewRequests(
    ) {
        return service.viewRequests();
    }

    // Chấp nhận yêu cầu

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/accept/{requestId}")
    public ResponseDto approveRequest(
            @PathVariable("requestId")
            Long requestId
    ) {
            service.approveRequest(requestId);
            ResponseDto dto = new ResponseDto();
            dto.setResponseMessage("Your request has been approved");
            return dto;

    }

    // từ chối yêu cầu

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reject/{requestId}")
    public  ResponseDto rejectRequest(
            @PathVariable("requestId")
            Long requestId

    ) {
          service.rejectRequest(requestId);
        ResponseDto dto = new ResponseDto();
        dto.setResponseMessage("Your request has been rejected");
        return dto;

    }




}
