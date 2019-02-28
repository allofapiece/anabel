package com.pinwheel.anabel.entity.setting;

import com.pinwheel.anabel.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class AccountSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "setting_id")
    private Setting setting;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "allowed_setting_value_id")
    private AllowedSettingValue allowedSettingValue;


    private String unconstrainedValue;

}
