package org.wise_economy.account_aggregator.domain;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseModel {

    @NotNull
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private String mobileNumber;

    @NotNull
    private Date dob;

    @NotNull
    private String perfiosUserHandle;

}
