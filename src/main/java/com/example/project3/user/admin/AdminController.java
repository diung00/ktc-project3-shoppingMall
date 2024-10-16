package com.example.project3.user.admin;

import com.example.project3.applyForBusiness.dto.RequestBusinessDto;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.shop.ShopDto;
import com.example.project3.shop.shopOpeningRequest.ShopRequestEntity;
import com.example.project3.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("getAllUsers")
    public List<UserDto> getAllUsers(){
      return adminService.readAllUsers();
    }

    @GetMapping("viewRequestsToBusiness")
    public List<RequestBusinessEntity> viewRequests(){
      return adminService.viewRequests();
    }

    // Chấp nhận yêu cầu
    @PutMapping("/acceptBusinessRequest/{requestId}")
    public RequestBusinessDto approveRequest(
            @PathVariable("requestId")
            Long requestId
    ) {
        return adminService.approveToBusiness(requestId);
    }

    @PutMapping("/rejectBusinessRequest/{requestId}")
    public RequestBusinessDto  rejectRequest(
            @PathVariable("requestId")
            Long requestId,
            @RequestBody
            RequestBusinessDto dto
    ){
       return adminService.rejectToBusiness(requestId, dto);
    }

    @GetMapping("viewShopRequests")
    public List<ShopRequestEntity> viewShopRequests(){
       return adminService.viewShopRequests();
    }
    @GetMapping("viewAllShops")
    public List<ShopDto> viewAllShops(){
        return adminService.viewAllShop();
    }

    @PutMapping("/approveOpening/{requestId}")
    public ShopRequestEntity approveOpening(
            @PathVariable("requestId")
            Long requestId
    ){
       return adminService.approveOpening(requestId);
    }

    @PutMapping("rejectOpening/{requestId}")
    public ShopRequestEntity rejectOpening(
            @PathVariable("requestId")
            Long requestId
    ){
        return adminService.rejectOpening(requestId);
    }

    @PutMapping("approveClosing/{requestId}")
    public ShopRequestEntity approveClosing(
            @PathVariable("requestId")
            Long requestId
    ){
        return adminService.approveClosing(requestId);
    }




























}
