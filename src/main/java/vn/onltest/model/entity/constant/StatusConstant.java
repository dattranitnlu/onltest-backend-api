package vn.onltest.model.entity.constant;


public class StatusConstant {

    public static final int ACTIVATION = 1;
    public static final int IN_ACTIVATION = 0;

    public static final int PENDING_APPROVAL = -1;
    public static final int APPROVED = 1; // ìf Approved => Activation => = 1
    public static final int REJECTED = -2;
}
