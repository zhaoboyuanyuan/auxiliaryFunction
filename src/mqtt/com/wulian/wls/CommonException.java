package mqtt.com.wulian.wls;

public enum CommonException
{
    /**
     * 
     * errorCode静态常量定义规则： {module}_{chileModule}_{error describe} 如：CLOUD_PARTERID_DONT_EXIT
     * COMMON_PASSWORD_ERROR error describe:一般为主谓宾结构
     * 
     * 如果可以在code上区分开ams/oms 比如ams固定数字开头
     * 
     * */
    
    /** 操作成功 */
    COMMON_SUCCESSFUL,
    
    /** 操作成功 */
    COMMON_DONE,
    
    /** 操作失败 */
    COMMON_FAIL,
    
    /** CMD非法 */
    COMMON_CMD_ILLEGAL,
    
    /** TOKEN失效错误 */
    COMMON_TOKEN_EXPIRE,
    
    /** 身份非法错误 */
    COMMON_IDENTITY_ILLEGAL,
    
    /** 系统内部错误 */
    COMMON_INTERNAL_ERROR,
    
    /** 生成token异常 */
    COMMON_TOKEN_CREATE_FAIL,
    
    /** 参数非法 */
    COMMON_PARAM_ILLEGAL,
    
    /** 参数为null */
    COMMON_PARAM_IS_NULL,
    
    /** 参数长度非法 */
    COMMON_PARAM_LENGTH_ILLEGAL,
    
    /** 参数格式非法 */
    COMMON_PARAM_FORMAT_ILLEGAL,
    
    /** 用户名以存在 */
    COMMON_USER_NAME_EXIST,
    
    /** 用户名不存在 */
    COMMON_USER_NAME_DONT_EXIST,
    
    /** 用户不存在 */
    COMMON_USER_DONT_EXIST,
    
    /** 手机号码已认证 */
    COMMON_PHONE_HAVE_REGISTERED,
    
    /** 手机号码未认证 */
    COMMON_PHONE_DONT_REGISTERED,
    
    /** 管理员密码不正确 */
    COMMON_ADMIN_PASSWORD_INCORRECT,
    
    /** 验证码失效 */
    COMMON_AUTHCODE_EXPIRED,
    
    /** 规则无效 */
    COMMON_RULE_EXPIRED,
    
    /** 时间受限 */
    COMMON_TIME_LIMITED,
    
    /** 次数受限 */
    COMMON_TIMES_LIMITED,
    
    /** 非法云用户 */
    // CLOUD_USER_ILLEGAL,
    
    /** 请求校验设备密码异常 */
    DEVICE_PASSWORD_VALIDATE_ERROR,
    
    /** 设备密码错误 */
    DEVICE_PASSWORD_INCORRECT,
    
    /** 设备已经绑定 */
    DEVICE_HAVE_BINDED,
    
    /** 设备未经绑定 */
    DEVICE_DONT_BINDED,
    
    /** 设备信息不存在 */
    DEVICE_INFO_DONT_EXIST,
    
    /** 设备版本不正确 */
    DEVICE_VERSION_INCORRECT,
    
    /** 设备信息丢失 */
    DEVICE_INFO_LOST,
    
    /** 设备不在线 */
    DEVICE_IS_OFFLINE,
    
    /** 用户信息不正确 */
    USER_INFO_INCORRECT,
    
    /** 用户类型不正确 */
    USER_TYPE_INCORRECT,
    
    /** 用户非法 */
    USER_ILLEGAL,
    
    /** UCenter非正常返回导致系统错误 */
    UCENTER_INTERNAL_ERROR,
    
    /** HTTP SERVLET处理消息异常（接收、发送） */
    HTTP_SERVLET_MESSAGE_HANDLER_ERROR,
    
    /** HTTP SERVLET转发异常 */
    HTTP_SERVLET_FORWARD_EXCEPTION,
    
    /** HTTP CLIENT异常 */
    HTTP_CLIENT_EXCEPTION,
    
    /** MQTT服务处理消息异常 */
    MQTT_SERVER_MESSAGE_HANDLER_ERROR,
    
    /** MQTT连接异常 */
    // MQTT_CONNECTION_EXCEPTION,
    
    /** JSON转换异常 */
    JSON_CONVERT_FAIL,
    
    /** 数据库操作异常 */
    DB_DATA_EXECUTE_FAIL,
    
    /** 发送邮件异常 */
    EMAIL_SEND_EXCEPTION,
    
    /** 邮箱未认证 */
    EMAIL_NOT_REGISTERED,
    
    /** 邮箱已认证 */
    EMAIL_HAVE_REGISTERED,
    
    /** 第三方客户已存在 */
    PARTNER_ID_IS_EXIST,
    
    /** 第三方客户不存在 */
    PARTNER_ID_IS_NOT_EXIST,
    
    /** 客户类型非法 */
    PARTNER_TYPE_ILLEGAL,
    
    /** 网关已被管理 */
    GATEWAY_GATEWAY_IS_CONTROL,
    
    /** sign不匹配 */
    HTTP_SIGN_DONT_MATCH,
    
    /** 城市不存在（天气） */
    WEATHER_CITY_IS_NOT_EXIST,
    
    /******************************************************************/
    VALIDATE_SIGNATURE_ERROR,
    
    COMPUTE_SIGNATURE_ERROR,
    
    ENCRYPT_AES_ERROR,
    
    DECRYPT_AES_ERROR,
    
    ILLEGAL_BUFFER,
    /******************************************************************/
    
    ERROR_TEMP;
}
