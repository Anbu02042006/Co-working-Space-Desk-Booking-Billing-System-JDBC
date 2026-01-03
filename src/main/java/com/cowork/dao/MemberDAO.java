package com.cowork.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cowork.bean.Member;
import com.cowork.util.DBUtil;

public class MemberDAO {
	public Member findMember(String memberID) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT * FROM MEMBER_TBL WHERE MEMBER_ID =?");
			ps.setString(1, memberID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Member m = new Member();
				m.setMemberID(rs.getString("MEMBER_ID"));
				m.setFullName(rs.getString("FULL_NAME"));
				m.setEmail(rs.getString("EMAIL"));
				m.setMobile(rs.getString("MOBILE"));
				m.setMembershipTier(rs.getString("MEMBERSHIP_TIER"));
				m.setOutstandingBalance(rs.getDouble("OUTSTANDING_BALANCE"));
				return m;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public List<Member> viewAllMembers() {
		List<Member> memberList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT * FROM MEMBER_TBL ORDER BY MEMBER_ID");
			rs = ps.executeQuery();
			if (rs.next()) {
				Member m = new Member();
				m.setMemberID(rs.getString("MEMBER_ID"));
				m.setFullName(rs.getString("FULL_NAME"));
				m.setEmail(rs.getString("EMAIL"));
				m.setMobile(rs.getString("MOBILE"));
				m.setMembershipTier(rs.getString("MEMBERSHIP_TIER"));
				m.setOutstandingBalance(rs.getDouble("OUTSTANDING_BALANCE"));
				memberList.add(m);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return memberList;
	}

	public boolean insertMember(Member member) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("INSERT INTO MEMBER_TBL VALUES(?,?,?,?,?,?)");
			ps.setString(1, member.getMemberID());
			ps.setString(2, member.getFullName());
			ps.setString(3, member.getEmail());
			ps.setString(4, member.getMobile());
			ps.setString(5, member.getMembershipTier());
			ps.setDouble(6, member.getOutstandingBalance());
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOutstandingBalance(String memberID, double newBalance) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("UPDATE MEMBER_TBL SET OUTSTANDING_BALANCE=? WHERE MEMBER_ID=? ");
			ps.setDouble(1, newBalance);
			ps.setString(2, memberID);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteMember(String memberID) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("DELETE FROM MEMBER_TBL WHERE MEMBER_ID=? ");
			ps.setString(1, memberID);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
