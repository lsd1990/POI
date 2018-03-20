package com.lsd.excel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ReceiveOrder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6328230499722650300L;

    private BigInteger receiveOrderFid;                 // 收货单流水号
    private Integer branchNum;                      // 门店编号
    private Integer storehouseNum;                  // 仓库编号
    private Integer supplierNum;                    // 供应商编号
    private String receiveOrderOperator;            // 采购员
    private Date receiveOrderPaymentDate;           // 约定付款日期
    private String receiveOrderMemo;                // 备注
    private Integer receiveOrderStateCode;          // 状态编号
    private String receiveOrderStateName;           // 状态名称
    private String receiveOrderCreator;             // 制单人
    private String receiveOrderAuditor;             // 审核人
    private BigDecimal receiveOrderTotalMoney;      // 合计金额
    private BigDecimal receiveOrderDiscountMoney;   // 优惠金额
    private BigDecimal receiveOrderDueMoney;        // 应付金额
    private BigDecimal receiveOrderPaidMoney;       // 已付金额
    private Date receiveOrderLastestPaymentDate;    // 最后付款日期
    private Date receiveOrderCreateTime;            // 制单日期
    private Date receiveOrderAuditTime;             // 审核日期
    private String receiveOrderBizday;              // 日期索引
    private Integer receiveOrderCarried;  			//日结标记
    private Integer departmentNum;				//部门
    private Date receiveOrderOperateDate;			//日结日期
    
    public BigInteger getReceiveOrderFid() {
        return receiveOrderFid;
    }

    public void setReceiveOrderFid(BigInteger receiveOrderFid) {
        this.receiveOrderFid = receiveOrderFid;
    }

    public Integer getBranchNum() {
        return branchNum;
    }

    public void setBranchNum(Integer branchNum) {
        this.branchNum = branchNum;
    }

    public Integer getStorehouseNum() {
        return storehouseNum;
    }

    public void setStorehouseNum(Integer storehouseNum) {
        this.storehouseNum = storehouseNum;
    }

    public Integer getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(Integer supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getReceiveOrderOperator() {
        return receiveOrderOperator;
    }

    public void setReceiveOrderOperator(String receiveOrderOperator) {
        this.receiveOrderOperator = receiveOrderOperator;
    }

    public Date getReceiveOrderPaymentDate() {
        return receiveOrderPaymentDate;
    }

    public void setReceiveOrderPaymentDate(Date receiveOrderPaymentDate) {
        this.receiveOrderPaymentDate = receiveOrderPaymentDate;
    }

    public String getReceiveOrderMemo() {
        return receiveOrderMemo;
    }

    public void setReceiveOrderMemo(String receiveOrderMemo) {
        this.receiveOrderMemo = receiveOrderMemo;
    }

    public Integer getReceiveOrderStateCode() {
        return receiveOrderStateCode;
    }

    public void setReceiveOrderStateCode(Integer receiveOrderStateCode) {
        this.receiveOrderStateCode = receiveOrderStateCode;
    }

    public String getReceiveOrderStateName() {
        return receiveOrderStateName;
    }

    public void setReceiveOrderStateName(String receiveOrderStateName) {
        this.receiveOrderStateName = receiveOrderStateName;
    }

    public String getReceiveOrderCreator() {
        return receiveOrderCreator;
    }

    public void setReceiveOrderCreator(String receiveOrderCreator) {
        this.receiveOrderCreator = receiveOrderCreator;
    }

    public String getReceiveOrderAuditor() {
        return receiveOrderAuditor;
    }

    public void setReceiveOrderAuditor(String receiveOrderAuditor) {
        this.receiveOrderAuditor = receiveOrderAuditor;
    }

    public BigDecimal getReceiveOrderTotalMoney() {
        return receiveOrderTotalMoney;
    }

    public void setReceiveOrderTotalMoney(BigDecimal receiveOrderTotalMoney) {
        this.receiveOrderTotalMoney = receiveOrderTotalMoney;
    }

    public BigDecimal getReceiveOrderDiscountMoney() {
        return receiveOrderDiscountMoney;
    }

    public void setReceiveOrderDiscountMoney(
            BigDecimal receiveOrderDiscountMoney) {
        this.receiveOrderDiscountMoney = receiveOrderDiscountMoney;
    }

    public BigDecimal getReceiveOrderDueMoney() {
        return receiveOrderDueMoney;
    }

    public void setReceiveOrderDueMoney(BigDecimal receiveOrderDueMoney) {
        this.receiveOrderDueMoney = receiveOrderDueMoney;
    }

    public BigDecimal getReceiveOrderPaidMoney() {
        return receiveOrderPaidMoney;
    }

    public void setReceiveOrderPaidMoney(BigDecimal receiveOrderPaidMoney) {
        this.receiveOrderPaidMoney = receiveOrderPaidMoney;
    }

    public Date getReceiveOrderLastestPaymentDate() {
        return receiveOrderLastestPaymentDate;
    }

    public void setReceiveOrderLastestPaymentDate(
            Date receiveOrderLastestPaymentDate) {
        this.receiveOrderLastestPaymentDate = receiveOrderLastestPaymentDate;
    }

    public Date getReceiveOrderCreateTime() {
        return receiveOrderCreateTime;
    }

    public void setReceiveOrderCreateTime(Date receiveOrderCreateTime) {
        this.receiveOrderCreateTime = receiveOrderCreateTime;
    }

    public Date getReceiveOrderAuditTime() {
        return receiveOrderAuditTime;
    }

    public void setReceiveOrderAuditTime(Date receiveOrderAuditTime) {
        this.receiveOrderAuditTime = receiveOrderAuditTime;
    }

    public String getReceiveOrderBizday() {
        return receiveOrderBizday;
    }

    public void setReceiveOrderBizday(String receiveOrderBizday) {
        this.receiveOrderBizday = receiveOrderBizday;
    }


    public static ReceiveOrder getByFid(String fid, List<ReceiveOrder> list) {
        if (list == null) {
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            ReceiveOrder model = list.get(i);
            if (model.getReceiveOrderFid().equals(fid)) {
                return model;
            }
        }
        return null;
    }
    
    /**
     * 设置初始值
     */
    public void initValue(){
        if(this.receiveOrderPaidMoney == null){
            this.receiveOrderPaidMoney = BigDecimal.ZERO;
        }
    }

	public Integer getReceiveOrderCarried() {
		return receiveOrderCarried;
	}

	public void setReceiveOrderCarried(Integer receiveOrderCarried) {
		this.receiveOrderCarried = receiveOrderCarried;
	}

	public Integer getDepartmentNum() {
		return departmentNum;
	}

	public void setDepartmentNum(Integer departmentNum) {
		this.departmentNum = departmentNum;
	}

	public Date getReceiveOrderOperateDate() {
		return receiveOrderOperateDate;
	}

	public void setReceiveOrderOperateDate(Date receiveOrderOperateDate) {
		this.receiveOrderOperateDate = receiveOrderOperateDate;
	}
	
}