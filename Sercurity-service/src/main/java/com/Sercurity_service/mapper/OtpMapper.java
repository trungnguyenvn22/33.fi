package com.Sercurity_service.mapper;

import com.Sercurity_service.dto.request.ChangePasswordRequest;
import com.Sercurity_service.entity.Otp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OtpMapper {

//    @Mapping(source = "request.otpValue", target = "otpValue")
//    @Mapping(source = "request.email", target = "email")
//    @Mapping(target = "expTime", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "used", ignore = true)
    Otp requestToOpt(ChangePasswordRequest request);
}
