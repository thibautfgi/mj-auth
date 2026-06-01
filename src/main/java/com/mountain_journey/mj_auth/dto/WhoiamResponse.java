package com.mountain_journey.mj_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WhoiamResponse {
    private String userEmail;
    private String userFirstName;
    private String userLastName;
}

