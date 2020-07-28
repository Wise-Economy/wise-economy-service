package org.wise_economy.account_aggregator.domain;


import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AttributeOverride(name = "id", column = @Column(name = "account_id"))
public class Account extends BaseModel{

    @ManyToOne
    private User user;

    @NotNull
    private String fiType;

    @NotNull
    private String fipId;

    @NotNull
    private String accountType;

    @NotNull
    private String maskedAccountNumber;

    private String linkRefNumber;

    @Enumerated(EnumType.STRING)
    private ConsentStatus status;
}
