package com.jt.pojo;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@TableName("tb_order_shipping")
@Data
@Accessors(chain=true)
public class OrderShipping extends BasePojo{
	
	@TableId
    private String orderId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverState;//省

    private String receiverCity;

    private String receiverDistrict;   //区县

    private String receiverAddress;

    private String receiverZip;  //邮政编码
    
}