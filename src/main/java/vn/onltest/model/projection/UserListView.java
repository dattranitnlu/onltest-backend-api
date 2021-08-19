package vn.onltest.model.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;

public interface UserListView {
    String getUsername();
    String getEmail();
    String getPhone();
    String getAddress();
    String getFullName();
    int getStatus();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date getDateOfBirth();

    @Value("#{target.gender == 'm'? 'Male' : 'Female'}")
    String getGender();

    Collection<RoleInfoSummary> getRoles();
}
