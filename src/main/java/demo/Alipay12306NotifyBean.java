package demo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by roger.zhang on 14-12-4.
 */
public class Alipay12306NotifyBean implements Serializable {


    private static final long serialVersionUID = -4289754940293988256L;

    public static final String PURCHASE_STATUS_EXCEPTION = "1";//采购状态：异常
    public static final String PURCHASE_STATUS_SUCCESS = "2";//采购状态：成功
    public static final String PURCHASE_STATUS_FAILED = "3";//采购状态：失败

    /**
     * 业务单号
     * case 1：原生单号
     * case 2：改签单号
     */
    private String businessNo;

    /**
     * 第三方银行服务商的商户号 必填
     */
    private String purchaseMerchantId;

    /**
     *采购支付时间 必填
     */
    private String purchaseDate;

    /**
     *商户系统生成的采购流水号 必填
     */
    private String purchaseNo;

    /**
     *商户在B2B平台下单的流水号 必填
     */
    private String otaPayId;

    /**
     *第三方服务商返回的流水 必填
     */
    private String otaOrderNo;

    /**
     *采购支付状态 必填: 1表示异常，2表示成功
     */
    private String purchaseStatus;

    /**
     *支付金额 必填
     */
    private String purchaseAmount;

    /**
     * 用户自己已支付
     */
    private boolean userPaid;

    public String getPurchaseMerchantId() {
        return purchaseMerchantId;
    }

    public void setPurchaseMerchantId(String purchaseMerchantId) {
        this.purchaseMerchantId = purchaseMerchantId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getOtaPayId() {
        return otaPayId;
    }

    public void setOtaPayId(String otaPayId) {
        this.otaPayId = otaPayId;
    }

    public String getOtaOrderNo() {
        return otaOrderNo;
    }

    public void setOtaOrderNo(String otaOrderNo) {
        this.otaOrderNo = otaOrderNo;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public boolean isUserPaid() {
        return userPaid;
    }

    public void setUserPaid(boolean userPaid) {
        this.userPaid = userPaid;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
