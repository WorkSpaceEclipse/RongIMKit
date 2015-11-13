package com.android.tools;

public class GiftObject {
//礼品
	private String id;
	private String name;
	private String num;//剩余数量
	private String remark;//含义
	private String price;//售价
	private String integral;//积分
	private String charm;//魅力
	private String pic;//魅力
	public String getId() {
		return id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCharm() {
		return charm;
	}
	public void setCharm(String charm) {
		this.charm = charm;
	}

}
