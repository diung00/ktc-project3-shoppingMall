package com.example.project3.item;

import com.example.project3.shop.ShopEntity;
import com.example.project3.shop.ShopRepository;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public ShopEntity findShopByOwnerId(Long ownerId) {
        Optional<User> user = userRepository.findById(ownerId);
        if (!user.isPresent()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ShopEntity shop = user.get().getShop();
        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shop;
    }


    public ItemDto createItem(
            Long ownerId,
            ItemDto itemDto
    ) {
        ShopEntity shop = findShopByOwnerId(ownerId);
        if (!shop.getOwner().getId().equals(ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this shop.");
        }

        ItemEntity newItem = new ItemEntity();
        newItem.setName(itemDto.getName());
        newItem.setDescription(itemDto.getDescription());
        newItem.setImageUrl(itemDto.getImageUrl());
        newItem.setPrice(itemDto.getPrice());
        newItem.setStock(itemDto.getStock());
        newItem.setShop(shop);
        newItem = itemRepository.save(newItem);
        return ItemDto.fromEntity(newItem);
    }

    public ItemDto updateItem(
            Long ownerId,
            Long itemId,
            ItemDto itemDto
    ) {
        ShopEntity shop = findShopByOwnerId(ownerId);
        Optional<ItemEntity> item = itemRepository.findByIdAndShop(itemId, shop);
        if (item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ItemEntity target = item.get();
        target.setName(itemDto.getName());
        target.setDescription(itemDto.getDescription());
        target.setImageUrl(itemDto.getImageUrl());
        target.setPrice(itemDto.getPrice());
        target.setStock(itemDto.getStock());

        return ItemDto.fromEntity(itemRepository.save(target));
    }

    public void deleteItem(
            Long ownerId,
            Long itemId
    ) {
        ShopEntity shop = findShopByOwnerId(ownerId);
        Optional<ItemEntity> item = itemRepository.findByIdAndShop(itemId, shop);
        if (item.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        itemRepository.delete(item.get());
    }




}
