package com.cowork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cowork.bean.Bill;
import com.cowork.util.DBUtil;

public class BillDAO {
	public int generateBillID() {
		int billID = 610001;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT NVL(MAX(BILL_ID),610000)+1 FROM BILL_TBL");
			rs = ps.executeQuery();
			if (rs.next()) {
				billID = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return billID;
	}

	public boolean recordBill(Bill bill) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("INSERT INTO BILL_TBL VALUES(?,?,?,?,?,?,?)");
			ps.setInt(1, bill.getBillID());
			ps.setString(2, bill.getMemberID());
			ps.setDate(3, new Date(bill.getBillingPeriodFrom().getTime()));
			ps.setDate(4, new Date(bill.getBillingPeriodTo().getTime()));
			ps.setDouble(5, bill.getTotalHours());
			ps.setDouble(6, bill.getAmount());
			ps.setString(7, bill.getStatus());
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean updateBillStatus(int billID, String status) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("UPDATE BILL_TBL SET STATUS=? WHERE BILL_ID=?");
			ps.setString(1, status);
			ps.setInt(2, billID);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public List<Bill> findPendingBillsByMember(String memberID) {
		List<Bill> billList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT * FROM BILL_TBL WHERE MEMBER_ID =? AND STATUS = 'PENDING'");
			ps.setString(1, memberID);
			rs = ps.executeQuery();
			while (rs.next()) {
				Bill b = new Bill();
				b.setBillID(rs.getInt("BILL_ID"));
				b.setMemberID(rs.getString("MEMBER_ID"));
				b.setBillingPeriodFrom(rs.getDate("BILLING_PERIOD_FROM"));
				b.setBillingPeriodTo(rs.getDate("BILLING_PERIOD_TO"));
				b.setTotalHours(rs.getDouble("TOTAL_HOURS"));
				b.setAmount(rs.getDouble("AMOUNT"));
				b.setStatus(rs.getString("STATUS"));
				billList.add(b);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return billList;
	}
}
