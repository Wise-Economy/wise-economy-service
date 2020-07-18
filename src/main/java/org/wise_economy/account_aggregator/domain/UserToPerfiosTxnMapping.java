package org.wise_economy.account_aggregator.domain;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class UserToPerfiosTxnMapping extends BaseModel {

    @ManyToOne
    private User user;

    @NotNull
    private String txnId;

}
