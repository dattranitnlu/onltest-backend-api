package vn.onltest.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.onltest.model.request.GroupModel;

@Getter
@Setter
@AllArgsConstructor
public class GroupChangeModel {
    private String type;
    private String action;
    private GroupModel groupModel;
    private String correlationId;
}
