package org.wise_economy.account_aggregator.dto.registration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Data
@ToString
public class RegistrationResponse {

    private boolean success;
    private Long userId;
}
