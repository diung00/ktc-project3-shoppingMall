package com.example.project3.shop;

import com.example.project3.applyForBusiness.dto.RequestDto;
import com.example.project3.shop.shopOpeningRequest.OpenRequestEntity;
import com.example.project3.shop.shopOpeningRequest.OpenRequestRepo;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final OpenRequestRepo openRequestRepo;

    public ShopService(
            ShopRepository shopRepository,
            UserRepository userRepository,
            OpenRequestRepo openRequestRepo
    ) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.openRequestRepo = openRequestRepo;
    }

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
        //ktra xem shop đã duoc open chua
        if (shopRepository.existsByOwnerAndStatus(user, Status.OPEN)) {
            throw new ResponseStatusException(HttpStatus.ACCEPTED);
        }

        if (user.getShop().getName() == null ||
                user.getShop().getDescription() == null ||
                user.getShop().getCategory() == null
        ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shop's information is incomplete");
        }

        OpenRequestEntity request = new OpenRequestEntity();
        request.setUser(user);
        request.setReason(dto.getReason());
        request.setStatus(Status.PENDING);
        return openRequestRepo.save(request);

    }

    public OpenRequestEntity closeRequest(Long userId, RequestDto dto){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        ShopEntity shop = user.getShop();

        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not own a shop.");
        }

        // Kiểm tra trạng thái của shop
        if (shop.getStatus() == Status.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shop is not opened yet.");
        }

        if (shop.getStatus() == Status.CLOSED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shop is already closed.");
        }

        if (shop.getStatus() == Status.CLOSE_REQUESTING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shop closing request is submitted.");
        }

        OpenRequestEntity request = new OpenRequestEntity();
        request.setUser(user);
        request.setReason(dto.getReason());
        request.setStatus(Status.CLOSE_REQUESTING);

        return openRequestRepo.save(request);
    }

    public List<OpenRequestEntity> viewShopRequests() {
        return openRequestRepo.findAll();
    }

    public OpenRequestEntity approveOpening(Long requestId){
        OpenRequestEntity request = openRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(Status.OPEN);
        return openRequestRepo.save(request);
    }

    public OpenRequestEntity rejectOpening(Long requestId){
        OpenRequestEntity request = openRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(Status.PENDING);
        return openRequestRepo.save(request);
    }


    public OpenRequestEntity approveClosing(Long requestId){
        OpenRequestEntity request = openRequestRepo.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        request.setStatus(Status.CLOSED);
        return openRequestRepo.save(request);
    }





















}