package uk.co.quartzcraft.core.systems.perms;

import uk.co.quartzcraft.core.util.Util;
import com.google.common.collect.Maps;

import java.util.Map;

public enum PermType {
    COSMETICS,
    SUPPORTER,
    RESERVED_SLOT,
    BONUS_XP,
    BONUS_TOKENS,
    BONUS_SPEED,
    BONUS_RUPEES,
    MINING_SPEED,
    JUMP_BOOST;

    static Map<Integer, PermType> permIdMap = Maps.newHashMap();
    static {
        for (PermType type : values()) {
            permIdMap.put(type.id(), type);
        }
    }
    public String name;
    public String perm;
    public String desc;
    public Long maxLevel;
    public int id() {
        return ordinal() + 1;
    }

    public static PermType getById(int id) {
        return permIdMap.get(id);
    }
}
