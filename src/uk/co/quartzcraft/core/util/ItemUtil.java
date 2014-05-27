package uk.co.quartzcraft.core.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.quartzcraft.core.features.FinalItems;
import uk.co.quartzcraft.core.features.SoulboundItems;
import uk.co.quartzcraft.core.features.UnbreakableItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemUtil {
    private static int LOREMETA_INDEX = 30;
    private static String LOREMETA_TAG = "&&::META";

    public static ItemStack setName(ItemStack item, String name) {
        if (item == null) {
            throw new NullPointerException();
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);//TODO Add better colour support com.empireminecraft.util.Util preferable
        item.setItemMeta(meta);
        return item;
    }

    public static String getName(ItemStack item) {
        return item != null ? item.getItemMeta().getDisplayName() : null;
    }

    public static LoreBuilder buildLore(ItemStack item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return new LoreBuilder(item);
    }

    public static String getLore(ItemStack item, int index) {
        if (item == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            if (index < meta.getLore().size()) {
                return meta.getLore().get(index);
            }
        }
        return null;
    }

    public static ItemStack setMeta(ItemStack item, String key, String meta) {
        return item != null ? new LoreBuilder(item).setMeta(key, meta).save() : null;
    }

    public static String getMeta(ItemStack item, String key) {
        return getMeta(item, key, null);
    }

    public static String getMeta(ItemStack item, String key, String def) {
        return item != null ? new LoreBuilder(item).getMeta(key, def) : def;
    }

    public static boolean hasMeta(ItemStack item, String key) {
        return getMeta(item, key) != null;
    }

    public static void givePlayerItems(final Player player, final ItemStack... items) {
        // Ensures the code runs Sync (If already on sync thread, will run now)
        TaskChain.newChain()
                .add(new TaskChain.GenericTask() {
                    @Override
                    protected void run() {
                        boolean dropped = false;
                        for (ItemStack itemStack : items) {
                            ItemStack[] items = {itemStack};
                            player.getInventory().addItem(items);
                        }
                    }
                }).execute();
    }

    public static class LoreBuilder {
        private final ItemStack item;
        private final ItemMeta meta;
        private final List<String> lore;
        private int index = 0;

        /**
         * Initiates a builder and sets the index to the first empty line after the lore
         *
         * @param item
         */
        public LoreBuilder(ItemStack item) {
            this.item = item;
            this.meta = item.getItemMeta();

            if (item.hasItemMeta() && meta.hasLore()) {
                this.lore = meta.getLore();
            } else {
                this.lore = new ArrayList<>();
            }
            if (lore.size() <= LOREMETA_INDEX - 2) {
                index = lore.size();
            } else {
                for (int i = LOREMETA_INDEX - 2; i >= 0; i--) {
                    if (!lore.get(i).isEmpty()) {
                        index = i + 1;
                        break;
                    }
                }
            }
        }

        public HashMap<String, String> getMeta() {
            HashMap<String, String> meta = new HashMap<>();
            if (LOREMETA_INDEX + 1 < lore.size()) {
                for (int i = LOREMETA_INDEX + 1; i + 1 < lore.size(); i += 2) {
                    String key = lore.get(i);
                    String val = lore.get(i + 1);
                    meta.put(key, val);
                }
            }
            return meta;
        }

        /**
         * Clears all lore on this item, preserving metadata.
         *
         * @return
         */
        public LoreBuilder clear() {
            index = 0;
            if (lore.size() <= LOREMETA_INDEX) {
                lore.clear();
            } else {
                for (int i = 0; i < LOREMETA_INDEX; i++) {
                    lore.set(i, "");
                }
            }
            return this;
        }

        /**
         * Clears all metadata on this item, preserving lore.
         *
         * @return
         */
        public LoreBuilder clearMeta() {
            if (lore.size() > LOREMETA_INDEX) {
                for (int i = LOREMETA_INDEX; i < lore.size(); i++) {
                    lore.set(i, "");
                }
                for (int i = lore.size() - 1; i >= 0; i--) {
                    if (lore.get(i).isEmpty()) {
                        lore.remove(i);
                    } else {
                        break;
                    }
                }
            }
            return this;
        }

        public LoreBuilder setName(String name) {
            meta.setDisplayName(name);//TODO fix colours
            return this;
        }

        public LoreBuilder makeShiny() {
            ensure(LOREMETA_INDEX - 1);
            lore.set(LOREMETA_INDEX - 1, "&&::SHINY");
            return this;
        }

        public LoreBuilder makeSoulbound() {
            int oldindex = index;
            SoulboundItems.makeSoulbound(this);
            index = oldindex + 1;
            return this;
        }

        public LoreBuilder makeFinal() {
            int oldindex = index;
            FinalItems.makeFinal(this);
            index = oldindex + 1;
            return this;
        }

        public LoreBuilder makeUnbreakable() {
            int oldindex = index;
            UnbreakableItems.makeUnbreakable(this);
            index = oldindex + 1;
            return this;
        }

        /**
         * Changes the current index
         *
         * @param i
         * @return
         */
        public LoreBuilder index(int i) {
            index = i;
            return this;
        }

        /**
         * Shifts to the previous index
         *
         * @return
         */
        public LoreBuilder prev() {
            index++;
            return this;
        }

        /**
         * Shifts to the next index
         *
         * @return
         */
        public LoreBuilder next() {
            index++;
            return this;
        }

        /**
         * Sets the current index to an empty line
         *
         * @return
         */
        public LoreBuilder empty() {
            return add(index, "");
        }

        /**
         * Inserts a line of lore after the last used index, shifting up any following lines if they existed.
         *
         * @param line
         * @return
         */
        public LoreBuilder add(String line) {
            return add(index, line);
        }

        /**
         * Inserts the specified line at the index. If the line was already set, it will be shifted up 1.
         *
         * @param i
         * @param line
         * @return
         */
        public LoreBuilder add(int i, String line) {
            if (line == null) {
                return this;
            }
            ensure(i-1);
            lore.add(i, line);//TODO fix colours
            index = i + 1;

            while (lore.size() > LOREMETA_INDEX && !lore.get(LOREMETA_INDEX).equals(LOREMETA_TAG)) {
                lore.remove(LOREMETA_INDEX - 10);
            }
            return this;
        }

        /**
         * Sets the line at the specified index, replacing it if already there.
         *
         * @param i
         * @param line
         * @return
         */
        public LoreBuilder set(int i, String line) {
            ensure(i);

            lore.set(i, line);//TODO fix colours

            index = i + 1;
            return this;
        }

        /**
         * Stores meta data about this item in hidden lore fields
         *
         * @param key
         * @param line
         * @return
         */
        public LoreBuilder setMeta(String key, String line) {
            ensure(LOREMETA_INDEX);
            lore.set(LOREMETA_INDEX, LOREMETA_TAG);
            for (int i = LOREMETA_INDEX + 1; i < lore.size(); i += 2) {
                if (lore.get(i).equalsIgnoreCase(key)) {
                    if (i + 1 == lore.size()) { // Broken meta...
                        lore.add(i + 1, line);
                    } else {
                        lore.set(i + 1, line);
                    }
                    return this;
                }
            }
            // Add new
            lore.add(key);
            lore.add(line);
            return this;
        }

        /**
         * Gets the specified meta value out of the items lore, defaulting to null
         *
         * @param key
         * @return
         */
        public String getMeta(String key) {
            return getMeta(key, null);
        }

        /**
         * Gets the specified meta value out of the items lore, with supplied default
         *
         * @param key
         * @param def
         * @return
         */
        public String getMeta(String key, String def) {
            if (LOREMETA_INDEX + 1 < lore.size()) {
                for (int i = LOREMETA_INDEX + 1; i + 1 < lore.size(); i += 2) {
                    if (lore.get(i).equalsIgnoreCase(key)) {
                        return lore.get(i + 1);
                    }
                }
            }
            return def;
        }

        /**
         * @param idx
         */
        private void ensure(int idx) {
            // empty lines if they are not set.
            for (int i = lore.size(); i <= idx; i++) {
                lore.add(i, "");
            }
        }

        public ItemStack save() {

            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    }
}
