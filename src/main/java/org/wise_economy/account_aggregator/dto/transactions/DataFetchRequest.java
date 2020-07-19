package org.wise_economy.account_aggregator.dto.transactions;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class DataFetchRequest {

    @NotNull
    private Long userId;
}
