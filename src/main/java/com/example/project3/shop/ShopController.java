package com.example.project3.shop;

import com.example.project3.applyForBusiness.dto.RequestDto;
import com.example.project3.applyForBusiness.dto.ResponseDto;
import com.example.project3.shop.shopOpeningRequest.OpenRequestEntity;
import jakarta.servlet.http.PushBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop")
public class ShopController {
    ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PutMapping("/update/{userId}")
    public ResponseDto updateShop(
            @PathVariable("userId")
            Long userId,
            @RequestBody
            ShopDto shopDto
    ){
        ResponseDto dto = new ResponseDto();
        shopService.updateShop(userId,shopDto);
        dto.setResponseMessage("update shop success!");
        return dto;
    }

    @PostMapping("/request-open/{userId}")
    public ResponseDto requestOpenShop(
            @PathVariable("userId")
            Long userId,
            @RequestBody
            RequestDto requestDto
    ) {
        OpenRequestEntity request = shopService.requestOpenShop(userId,requestDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseMessage("Shop opening request submitted successfully!");
        return responseDto;
    }

    @PostMapping("/request-close/{userId}")
    public ResponseDto requestCloseShop(
            @PathVariable("userId")
            Long userId,
            @RequestBody
            RequestDto requestDto

    ){
        shopService.closeRequest(userId, requestDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseMessage("Shop closing request submitted successfully!");
        return responseDto;
    }





    @GetMapping("/view-shop-requests")
    public List<OpenRequestEntity> viewShopRequests() {
        return shopService.viewShopRequests();
    }

    @PutMapping("/approve-open/{requestId}")
    public ResponseDto approveOpening(
            @PathVariable("requestId")
            Long requestId
    ) {
        shopService.approveOpening(requestId);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseMessage("Your shop has been approved for opening!");
        return responseDto;
    }

    @PutMapping("/reject-open/{requestId}")
    public ResponseDto rejectOpening(
            @PathVariable("requestId")
            Long requestId
    ) {
        shopService.rejectOpening(requestId);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseMessage("Your shop opening request has been rejected!");
        return responseDto;
    }

    @PutMapping("/approve-close/{requestId}")
    public ResponseDto approveClosing(
            @PathVariable("requestId")
            Long requestId
    ) {
        shopService.approveClosing(requestId);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseMessage("Your shop has been closed!");
        return responseDto;
    }










}
