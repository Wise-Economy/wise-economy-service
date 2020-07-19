package org.wise_economy.account_aggregator.dto.perfios.datafetch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.wise_economy.account_aggregator.dto.perfios.callback.Account;

@Getter
@Setter
@ToString
@Data
@JsonInclude(Include.NON_NULL)
public class InitiateDataFetch {

    private String profileId;
    private String userId;
    private String txnId;
    private List<Account> accounts;
}


/*
{

  "profileId":"default_deposit_profile",

  "userId":"OTg4MDIwMzM2OA==",

  "txnId":"926d2473-96b0-4a21-a7ef-b50cd2a3251d",

  "accounts":[

        {

          "fipId": "ACME-FIP",

          "maskedAccNumber": "XXXX-XXXX-1234",

          "fiType": "DEPOSIT"

        },

        {

          "fipId": "ACME-FIP",

          "maskedAccNumber": "XXXX-XXXX-0099",

          "fiType": "DEPOSIT"

        }

  ]

}
*/