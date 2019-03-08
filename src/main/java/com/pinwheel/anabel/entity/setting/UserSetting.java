package com.pinwheel.anabel.entity.setting;

import com.pinwheel.anabel.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class UserSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "setting_id")
    private Setting setting;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "allowed_setting_value_id")
    private AllowedSettingValue allowedSettingValue;

    private String unconstrainedValue;
}
