package org.wise_economy.account_aggregator.dto.perfios.callback;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
@JsonInclude(Include.NON_NULL)
public class Account {

    private String fiType;
    private String fipId;
    private String accType;
    private String linkRefNumber;
    private String maskedAccNumber;
}
