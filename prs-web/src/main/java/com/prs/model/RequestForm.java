package com.prs.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;

@Entity
public class RequestForm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="userId")
	private Users user;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public LocalDate getDateNeeded() {
		return dateNeeded;
	}
	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	
	public static String incrementNum(String maxRNum)
	{
		String nextRNum = "";
        int num = Integer.parseInt(maxRNum.substring(7));
        num++;
        String numStr = String.format("%04d", num);
        nextRNum += numStr;
        return nextRNum;
	}
	
	public static String RNum()
    {
        StringBuilder requestNumber = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String rNumerals = LocalDate.now().format(formatter);

        requestNumber.append("R");
        requestNumber.append(rNumerals);

        return requestNumber.toString();
    }

}
