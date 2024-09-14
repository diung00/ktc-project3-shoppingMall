package com.example.project3.shop;

import com.example.project3.applyForBusiness.dto.RequestDto;
import com.example.project3.item.ItemDto;
import com.example.project3.item.ItemEntity;
import com.example.project3.item.ItemRepository;
import com.example.project3.shop.shopOpeningRequest.OpenRequestEntity;
import com.example.project3.shop.shopOpeningRequest.OpenRequestRepo;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.User;
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
    private final OpenRequestRepo openRequestRepo;
    private final ItemRepository itemRepository;



    public void getAShop(User user) {
        if (user.getShop() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has a shop");
        }

        ShopEntity newShop = new ShopEntity();
        newShop.setName("New Shop");
        newShop.setStatus(Status.PENDING);
        newShop.setOwner(user);

        shopRepository.save(newShop);

        user.setShop(newShop);
        userRepository.save(user);
    }


    public ShopEntity updateShop(Long userId, ShopDto shopDto) {
        Optional<User> owner = userRepository.findById(userId);
        if (owner.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ShopEntity shop = owner.get().getShop();
        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!shop.getOwner().getId().equals(userId)) {
            log.info(shop.getOwner().getId().toString());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());
        shop.setCategory(Category.valueOf(shopDto.getCategory()));

        return shopRepository.save(shop);
    }

    public OpenRequestEntity requestOpenShop(Long userId, RequestDto dto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        ShopEntity shop = user.getShop();

        if (!shop.getOwner().getId().equals(userId)) {
            log.info(shop.getOwner().getId().toString());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        //ktra xem shop đã duoc open chua
        if (shop.getStatus() == Status.OPEN){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (shop.getName() == null ||
                shop.getDescription() == null ||
                shop.getCategory() == null
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        OpenRequestEntity request = new OpenRequestEntity();
        request.setUser(user);
        request.setReason(dto.getReason());
        request.setStatus(Status.PENDING);
        shop.setStatus(Status.PENDING);
        return openRequestRepo.save(request);

    }

    public OpenRequestEntity closeRequest(Long userId, RequestDto dto){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        ShopEntity shop = user.getShop();

        if (!shop.getOwner().getId().equals(userId)) {
            log.info(shop.getOwner().getId().toString());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not own a shop.");
        }
        if (shop.getStatus() != Status.OPEN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

            OpenRequestEntity request = new OpenRequestEntity();
            request.setUser(user);
            request.setReason(dto.getReason());
            request.setStatus(Status.CLOSE_REQUESTING);
            shop.setStatus(Status.CLOSE_REQUESTING);

            return openRequestRepo.save(request);

    }

    public List<OpenRequestEntity> viewShopRequests() {
        return openRequestRepo.findAll();
    }

    public OpenRequestEntity approveOpening(Long requestId){
        OpenRequestEntity request = openRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(Status.OPEN);
        ShopEntity shop = request.getUser().getShop();
        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        shop.setStatus(Status.OPEN);
        shopRepository.save(shop);
        return openRequestRepo.save(request);
    }

    public OpenRequestEntity rejectOpening(Long requestId){
        OpenRequestEntity request = openRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(Status.PENDING);
        return openRequestRepo.save(request);
    }


    public OpenRequestEntity approveClosing(Long requestId){
        OpenRequestEntity request = openRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (request.getStatus() == Status.CLOSE_REQUESTING){
            request.setStatus(Status.CLOSED);
            ShopEntity shop = request.getUser().getShop();
            shop.setStatus(Status.CLOSED);
        }else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return openRequestRepo.save(request);
    }


    public List<ShopDto> searchShop(String shopName){

        List<ShopEntity> shopEntities = shopRepository.findByNameContainingOrderByIdDesc(shopName);
        return shopEntities.stream()
                .map(shop -> new ShopDto(shop.getName(), shop.getDescription(), shop.getCategory()))
                .collect(Collectors.toList());

    }

    public List<ShopDto> searchShopByCategory(String categoryStr){
        Category category = Category.valueOf(categoryStr.toUpperCase());
        List<ShopEntity> shopEntities = shopRepository.findByCategoryOrderByIdDesc(category);
        return shopEntities.stream()
                .map(shop -> new ShopDto(shop.getName(), shop.getDescription(), shop.getCategory()))
                .collect(Collectors.toList());

    }

    public List<ItemDto> findItem(
            String key,
            Double minPrice,
            Double maxPrice
    ){
      List<ItemEntity>items = itemRepository.findByNameContainingAndPriceRange(key, minPrice, maxPrice);

        List<ItemDto> itemDtos = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDto dto = new ItemDto();
            dto.setName(item.getName());
            dto.setPrice(item.getPrice());
            dto.setStock(item.getStock());
            dto.setDescription(item.getDescription());

            itemDtos.add(dto);
        }
        return itemDtos;
    }























}