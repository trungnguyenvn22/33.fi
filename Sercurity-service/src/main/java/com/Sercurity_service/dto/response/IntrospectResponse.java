package com.Sercurity_service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IntrospectResponse {
    boolean token_valid;
}
