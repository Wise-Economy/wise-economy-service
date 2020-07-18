package org.wise_economy.account_aggregator.dto.registration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Data
public class RegistrationRequest {

    @NotNull
    private String mobileNumber;
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    private String dob;
    @NotNull
    private String gender;
}
