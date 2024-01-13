package com.github.theword.event;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpigotPlayer {

    private String uuid;
    private String nickname;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("player_list_name")
    private String playerListName;
    private String address;

    @SerializedName("is_health_scaled")
    private boolean isHealthScaled;
    @SerializedName("health_scale")
    private double healthScale;

    private float exp;

    @SerializedName("total_exp")
    private int totalExp;
    private int level;

    private String locale;

    @SerializedName("player_time")
    private long playerTime;
    @SerializedName("is_player_time_relative")
    private boolean isPlayerTimeRelative;
    @SerializedName("player_time_offset")
    private long playerTimeOffset;
    @SerializedName("walk_speed")
    private float walkSpeed;
    @SerializedName("fly_speed")
    private float flySpeed;
    @SerializedName("allow_flight")
    private boolean allowFlight;
    @SerializedName("is_sprinting")
    private boolean isSprinting;
    @SerializedName("is_sneaking")
    private boolean isSneaking;
    @SerializedName("is_flying")
    private boolean isFlying;
    @SerializedName("is_op")
    private boolean isOp;

}
