package com.github.cozyplugins.testplugin.inventorys;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.reward.RewardBundle;
import com.github.cozyplugins.cozylibrary.reward.RewardBundleEditorInventory;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RewardInventory extends RewardBundleEditorInventory {

    /**
     * Used to create a reward bundle editor.
     */
    public RewardInventory() {
        super(new RewardBundle());
    }

    @Override
    protected void onBundleUpdate(@NotNull RewardBundle bundle) {

    }

    @Override
    protected @Nullable CozyInventory onBackButton(@NotNull PlayerUser user) {
        this.getBundle().giveRewardBundle(user);
        return null;
    }
}
