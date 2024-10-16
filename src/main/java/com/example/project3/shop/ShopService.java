package com.example.project3.shop;

import com.example.project3.config.AuthenticationFacade;
import com.example.project3.item.ItemDto;
import com.example.project3.item.ItemEntity;
import com.example.project3.item.ItemRepository;
import com.example.project3.shop.shopOpeningRequest.ShopRequestDto;
import com.example.project3.shop.shopOpeningRequest.ShopRequestEntity;
import com.example.project3.shop.shopOpeningRequest.ShopRequestRepo;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    private static final Logger log = LoggerFactory.getLogger(ShopService.class);
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopRequestRepo shopRequestRepo;
    private final ItemRepository itemRepository;
    private final AuthenticationFacade authFacade;



    public void getAShop(UserEntity user) {
        if (user.getShop() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a shop");
        }

        ShopEntity newShop = new ShopEntity();
        newShop.setName("New Shop");
        newShop.setStatus(ShopStatus.PENDING);
        newShop.setOwner(user);

        shopRepository.save(newShop);

        user.setShop(newShop);
        userRepository.save(user);
    }


    public ShopDto updateShop(ShopDto shopDto) {

        UserEntity owner = authFacade.getCurrentUserEntity();
        ShopEntity shop = owner.getShop();
        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());
        shop.setCategory(shopDto.getCategory());

        return ShopDto.fromEntity(shopRepository.save(shop));
    }

    public ShopRequestDto requestOpenShop(ShopRequestDto dto) {

        UserEntity owner = authFacade.getCurrentUserEntity();
        ShopEntity shop = owner.getShop();
        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!shop.getStatus().equals(ShopStatus.PENDING)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (shop.getName() == null ||
                shop.getDescription() == null ||
                shop.getCategory() == null
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ShopRequestEntity request = new ShopRequestEntity();
        request.setUser(owner);
        request.setReason(dto.getReason());
        request.setStatus(ShopStatus.PENDING);
        return ShopRequestDto.fromEntity(shopRequestRepo.save(request));

    }

    public ShopRequestDto requestCloseShop (ShopRequestDto dto){

        UserEntity owner = authFacade.getCurrentUserEntity();
        ShopEntity shop = owner.getShop();

        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!shop.getStatus().equals(ShopStatus.OPEN)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

            ShopRequestEntity request = new ShopRequestEntity();
            request.setUser(owner);
            request.setReason(dto.getReason());
            request.setStatus(ShopStatus.CLOSE_REQUESTING);
            shop.setStatus(ShopStatus.CLOSE_REQUESTING);

            return ShopRequestDto.fromEntity(shopRequestRepo.save(request));

    }
}