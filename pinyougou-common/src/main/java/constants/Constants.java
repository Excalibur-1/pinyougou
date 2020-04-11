package constants;

/**
 * @author gxl
 */
public interface Constants {
  String ADD_SUCCESS = "增加成功";

  String ADD_FAIL = "增加失败";

  String UPDATE_SUCCESS = "修改成功";

  String UPDATE_FAIL = "修改失败";

  String DELETE_SUCCESS = "删除成功";

  String DELETE_FAIL = "删除失败";

  String AUDIT_SUCCESS = "审核成功";

  String AUDIT_FAIL = "审核失败";

  String ILLEGAL_OPERATION = "非法操作";

  String UPLOAD_FAIL = "上传失败";

  /**
   * redis缓存key常量定义
   */
  interface RedisCacheKey {

    /**
     * 模板id
     */
    String ITEM_CAT = "itemCat";

    /**
     * 品牌列表
     */
    String BRAND_LIST = "brandList";

    /**
     * 规格列表
     */
    String SPEC_LIST = "specList";

    /**
     * 手机号注册验证码
     */
    String SMS_CODE = "smsCode";
  }
}
