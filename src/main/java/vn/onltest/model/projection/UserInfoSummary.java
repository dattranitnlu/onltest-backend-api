package vn.onltest.model.projection;

import java.util.Collection;

public interface UserInfoSummary {
    String getUsername();
    String getFullName();
    Collection<RoleInfoSummary> getRoles();

    interface RoleInfoSummary {
        String getName();
    }
}
