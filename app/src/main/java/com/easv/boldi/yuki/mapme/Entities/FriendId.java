package com.easv.boldi.yuki.mapme.Entities;

import android.support.annotation.NonNull;

/**
 * Created by yuki on 20/03/2018.
 */

public class FriendId {
    public String friendId;

    public <T extends FriendId> T withId(@NonNull final String id) {
        this.friendId = id;
        return (T) this;
    }
}
