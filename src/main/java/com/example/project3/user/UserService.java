package com.example.project3.user;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.example.project3.user.dto.UserDto;
import com.example.project3.user.entity.CustomUserDetails;
import com.example.project3.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService (
            UserRepository repository,
            PasswordEncoder passwordEncoder
    ) {
            this.repository = repository;
            this.passwordEncoder = passwordEncoder;

        User diu = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .nickname("diu")
                .name("diu")
                .age(20)
                .email("diu@example.com")
                .phone("010-1234-5678")
                .authorities("ROLE_ADMIN,READ,WRITE")
                .build();
        this.repository.save(diu);

        User user1 = User.builder()
                .username("alex")
                .password(passwordEncoder.encode("1234"))
                .nickname("user1")
                .name("user1")
                .age(30)
                .email("user1@example.com")
                .phone("010-1234-5678")
                .authorities("ROLE_USER")
                .build();
        this.repository.save(user1);
    }


    public UserDto createUser(UserDto dto) {
        if (userExists(dto.getUsername()) || !dto.getPassword().equals(dto.getPassCheck()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setAuthorities("ROLE_DEFAULT");

        newUser =  repository.save(newUser);
        return UserDto.fromEntity(newUser);
    }

    public List<UserDto> readAllUsers() {
        List<UserDto> userList = new ArrayList<>();
        for (User user : repository.findAll()) {
           userList.add(UserDto.fromEntity(user));
        }
        return  userList;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<User> optionalUser =
                repository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);

        return CustomUserDetails.fromEntity(optionalUser.get());

    }


    public UserDto getUserByUsername(String username) {
        Optional<User> optionalUser = repository.findByUsername(username);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return UserDto.fromEntity(user);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public UserDto readUser(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return UserDto.fromEntity(user);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public UserDto updateUser(Long id, UserDto dto) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()){
            User target = optionalUser.get();

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

            }else {target.setAuthorities("ROLE_USER");}

            return UserDto.fromEntity(repository.save(target));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public UserDto updateProfileImg(Long id, MultipartFile image) {

        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // tạo không gian lưu file
        String profileDir = "media/" + id + "/";
        try{
            Files.createDirectories(Path.of(profileDir));
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String originalFilename = image.getOriginalFilename();
        String[] filenameSplit = originalFilename.split("\\.");
        String extension = filenameSplit[filenameSplit.length - 1];

        String uploadPath = profileDir + "profile." + extension;
        try{
            image.transferTo(Path.of(uploadPath));
        }catch(IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //nếu upload thành công, lưu url của ảnh vào entity
        String regPath = "/static/" + id + "/profile." + extension;
        User target = optionalUser.get();
        target.setProfileImgUrl(regPath);

        //đổi entity sang dto và trả về kết quả
        return UserDto.fromEntity(repository.save(target));
    }


    public void deleteUser(Long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }

    public boolean userExists(
            String username
    ){
        return repository.existsByUsername(username);
    }




}
