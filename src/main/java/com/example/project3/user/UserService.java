package com.example.project3.user;

import com.example.project3.config.AuthenticationFacade;
import com.example.project3.item.ItemDto;
import com.example.project3.item.ItemEntity;
import com.example.project3.item.ItemRepository;
import com.example.project3.jwt.JwtTokenUtils;
import com.example.project3.jwt.dto.JwtRequestDto;
import com.example.project3.jwt.dto.JwtResponseDto;
import com.example.project3.shop.Category;
import com.example.project3.shop.ShopDto;
import com.example.project3.shop.ShopEntity;
import com.example.project3.shop.ShopRepository;
import com.example.project3.user.dto.UserCreateDto;
import com.example.project3.user.dto.UserDto;
import com.example.project3.config.CustomUserDetails;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationFacade authFacade;
    private final ShopRepository shopRepository;
    private ItemRepository itemRepository;

    @Transactional
    public UserDto createUser(UserCreateDto dto) {
        if (repository.existsByUsername(dto.getUsername()) || !dto.getPassword().equals(dto.getPassCheck()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        UserEntity newUser = new UserEntity();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setAuthorities("ROLE_DEFAULT");

        return UserDto.fromEntity(repository.save(newUser));
    }

    public JwtResponseDto login(JwtRequestDto dto) {
        UserEntity userEntity = repository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                userEntity.getPassword()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        String jwt = jwtTokenUtils.generateToken(CustomUserDetails.fromEntity(userEntity));
        JwtResponseDto response = new JwtResponseDto();
        response.setToken(jwt);
        return response;
    }


    public UserDto readUser() {
        UserEntity userEntity = authFacade.getCurrentUserEntity();

        return UserDto.fromEntity(userEntity);

    }

    public UserDto updateUser(UserDto dto) {
        UserEntity target = authFacade.getCurrentUserEntity();

        target.setPassword(passwordEncoder.encode(dto.getPassword()));
        target.setNickname(dto.getNickname());
        target.setName(dto.getName());
        target.setAge(dto.getAge());
        target.setEmail(dto.getEmail());
        target.setPhone(dto.getPhone());
        if (
                dto.getNickname() == null ||
                        dto.getName() == null ||
                        dto.getAge() == null ||
                        dto.getEmail() == null ||
                        dto.getPhone() == null

        ) {
            target.setAuthorities("ROLE_DEFAULT");

        } else {
            target.setAuthorities("ROLE_USER");
        }

        return UserDto.fromEntity(repository.save(target));
    }


    public String updateProfileImg(MultipartFile image) {

        UserEntity userEntity = authFacade.getCurrentUserEntity();
        Long id = userEntity.getId();

        // tạo không gian lưu file
        String profileDir = "media/" + id + "/";
        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String originalFilename = image.getOriginalFilename();
        String[] filenameSplit = originalFilename.split("\\.");
        String extension = filenameSplit[filenameSplit.length - 1];

        String uploadPath = profileDir + "profile." + extension;
        try {
            image.transferTo(Path.of(uploadPath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userEntity.setProfileImgUrl(uploadPath);

        return "Saved Profile Image!";
    }


    public List<ShopDto> searchShop(String shopName){

        List<ShopEntity> shopEntities = shopRepository.findByNameContainingOrderByIdDesc(shopName);
        return shopEntities.stream()
                .map(shop -> new ShopDto(shop.getName(), shop.getDescription(), shop.getCategory(), shop.getStatus()))
                .collect(Collectors.toList());

    }

    public List<ShopDto> searchShopByCategory(String categoryStr){
        Category category = Category.valueOf(categoryStr.toUpperCase());
        List<ShopEntity> shopEntities = shopRepository.findByCategoryOrderByIdDesc(category);
        return shopEntities.stream()
                .map(shop -> new ShopDto(shop.getName(), shop.getDescription(), shop.getCategory(), shop.getStatus()))
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