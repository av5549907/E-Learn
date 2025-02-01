package com.elearn.app.ApiResponse;

import com.elearn.app.dtos.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtApiResponse {
      private String token;
      private UserDto userDto;
}
