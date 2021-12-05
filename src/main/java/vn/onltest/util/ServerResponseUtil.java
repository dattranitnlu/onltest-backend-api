package vn.onltest.util;

public class ServerResponseUtil {
    /**
     * This means that requests is successfully.
     */
    public static final int SUCCEED_CODE = 200;

    /**
     * This means that client-side input fails validation.
     */
    public static final int BAD_REQUEST_CODE = 400;

    /**
     * This means the user isn’t not authorized to access a resource.
     * It usually returns when the user isn’t authenticated.
     */
    public static final int UNAUTHORIZED_CODE = 401;

    /**
     * This means the user is authenticated, but it’s not allowed to access a resource.
     */
    public static final int NOT_ALLOWED_CODE = 403;

    /**
     * This indicates that a resource is not found.
     */
    public static final int NOT_FOUND_DATA_CODE = 404;

    /**
     * This is a generic server error. It probably shouldn’t be thrown explicitly.
     */
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;

    /**
     * This indicates an invalid response from an upstream server.
     */
    public static final int BAD_GATEWAY_CODE = 502;

    /**
     * This indicates that something unexpected happened on server side
     * (It can be anything like server overload, some parts of the system failed, etc.).
     */
    public static final int SERVICE_UNAVAILABLE_CODE = 503;

    public static final String STATUS_200_MESSAGE = "Thành công";
    public static final String STATUS_400_REASON = "Yêu cầu chưa chính xác";
    public static final String STATUS_401_REASON = "Chưa xác thực đăng nhập";
    public static final String STATUS_403_REASON = "Truy cập bị cấm";
    public static final String STATUS_404_REASON = "Không tìm thấy";
    public static final String STATUS_500_REASON = "Lỗi xử lý bên trong code";
}
