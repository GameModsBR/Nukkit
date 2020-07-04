package cn.nukkit.permission;

import com.sun.istack.internal.NotNull;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class PermissionAttachmentInfo {

    private Permissible permissible;

    private String permission;

    private PermissionAttachment attachment;

    private boolean value;

    public PermissionAttachmentInfo(Permissible permissible, @NotNull String permission, PermissionAttachment attachment, boolean value) {
        this.permissible = permissible;
        this.permission = permission;
        this.attachment = attachment;
        this.value = value;
    }

    public Permissible getPermissible() {
        return permissible;
    }

    public String getPermission() {
        return permission;
    }

    public PermissionAttachment getAttachment() {
        return attachment;
    }

    public boolean getValue() {
        return value;
    }
}
