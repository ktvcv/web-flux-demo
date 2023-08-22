package com.example.webfluxdemo.mapper;

import com.example.webfluxdemo.dto.UserDto;
import com.example.webfluxdemo.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserDto map(UserEntity entity);

     @InheritInverseConfiguration
     UserEntity map(UserDto userDto);
}

