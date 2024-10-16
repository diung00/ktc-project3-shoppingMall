package com.example.project3.user.admin;

import com.example.project3.applyForBusiness.BusinessRepository;
import com.example.project3.applyForBusiness.RequestStatus;
import com.example.project3.applyForBusiness.dto.RequestBusinessDto;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.shop.*;
import com.example.project3.shop.shopOpeningRequest.ShopRequestEntity;
import com.example.project3.shop.shopOpeningRequest.ShopRequestRepo;
import com.example.project3.user.UserRepository;
import com.example.project3.user.dto.UserDto;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final ShopService shopService;
    private final ShopRequestRepo shopRequestRepo;
    private final ShopRepository shopRepository;


    public List<UserDto> readAllUsers() {
        List<UserDto> userList = new ArrayList<>();
        for (UserEntity user : userRepository.findAll()) {
            userList.add(UserDto.fromEntity(user));
        }
        return  userList;
    }

    public List<RequestBusinessEntity> viewRequests(){
        return businessRepository.findAll();
    }


    //method chấp nhận yêu cầu tro thanh owner
    public RequestBusinessDto approveToBusiness(Long requestId) {
        RequestBusinessEntity request = businessRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = request.getUser();

        String authorities = user.getAuthorities();
        if (!authorities.contains("ROLE_BUSINESS")) {
            user.setAuthorities("ROLE_BUSINESS");
            shopService.getAShop(user);
            userRepository.save(user);
        }
        request.setStatus(RequestStatus.APPROVED);
        return RequestBusinessDto.fromEntity(businessRepository.save(request));
    }


        // Từ chối yêu cầu tro thanh owner
        public RequestBusinessDto rejectToBusiness(Long requestId, RequestBusinessDto dto) {
            RequestBusinessEntity request = businessRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            request.setStatus(RequestStatus.REJECTED);
            request.setReason(dto.getReason());
            return RequestBusinessDto.fromEntity(businessRepository.save(request));

        }

        public List<ShopRequestEntity> viewShopRequests() {
        return shopRequestRepo.findAll();
    }

        public List<ShopDto> viewAllShop() {
        return shopRepository.findAll()
                .stream()
                .map(shop -> new ShopDto(shop.getName(), shop.getDescription(), shop.getCategory(), shop.getStatus()))
                .collect(Collectors.toList());
    }



    public ShopRequestEntity approveOpening(Long requestId){
        ShopRequestEntity request = shopRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(ShopStatus.OPEN);
        ShopEntity shop = request.getUser().getShop();
        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        shop.setStatus(ShopStatus.OPEN);
        shopRepository.save(shop);
        return shopRequestRepo.save(request);
    }

    public ShopRequestEntity rejectOpening(Long requestId){
        ShopRequestEntity request = shopRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(ShopStatus.PENDING);
        return shopRequestRepo.save(request);
    }


    public ShopRequestEntity approveClosing(Long requestId){
        ShopRequestEntity request = shopRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (request.getStatus() == ShopStatus.CLOSE_REQUESTING){
            request.setStatus(ShopStatus.CLOSED);
            ShopEntity shop = request.getUser().getShop();
            shop.setStatus(ShopStatus.CLOSED);
        }else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return shopRequestRepo.save(request);
    }


}





