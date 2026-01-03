package com.cowork.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cowork.bean.Booking;
import com.cowork.util.DBUtil;

public class BookingDAO {
	public int generateBookingID() {
		int bookingID = 510001;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT NVL(MAX(BOOKING_ID), 510000) + 1 FROM BOOKING_TBL");
			rs = ps.executeQuery();
			if (rs.next()) {
				bookingID = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return bookingID;
	}

	public boolean recordBooking(Booking booking) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("INSERT INTO BOOKING_TBL VALUES(?,?,?,?,?,?,?)");
			ps.setInt(1, booking.getBookingID());
			ps.setString(2, booking.getMemberID());
			ps.setString(3, booking.getDeskCode());
			ps.setDate(4, new Date(booking.getBookingDate().getTime()));
			ps.setString(5, booking.getStartTime());
			ps.setString(6, booking.getEndTime());
			ps.setString(7, booking.getStatus());
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean updateBookingStatus(int bookingID, String status) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("UPDATE MEMBER_TBL SET STATUS=? WHERE BOOKING_ID=?");
			ps.setString(1, status);
			ps.setInt(2, bookingID);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public List<Booking> findBookingsByMember(String memberID) {
		List<Booking> bookingList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT * FROM BOOKING_TBL WHERE MEMBER_ID=?");
			ps.setString(1, memberID);
			rs = ps.executeQuery();
			if (rs.next()) {
				Booking b = new Booking();
				b.setBookingID(rs.getInt("BOOKING_ID"));
				b.setMemberID(rs.getString("MEMBER_ID"));
				b.setDeskCode(rs.getString("DESK_CODE"));
				b.setBookingDate(rs.getDate("BOOKING_DATE"));
				b.setStartTime(rs.getString("START_TIME"));
				b.setEndTime(rs.getString("END_TIME"));
				b.setStatus(rs.getString("STATUS"));
				bookingList.add(b);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return bookingList;
	}

	public List<Booking> findBookingsForDeskAndDate(String deskCode, java.util.Date bookingDate) {
		List<Booking> bookingList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getDBConnection();
			ps = conn.prepareStatement("SELECT * FROM BOOKING_TBL WHERE DESK_CODE =? AND BOOKING_DATE =?");
			ps.setString(1, deskCode);
			ps.setDate(2, new java.sql.Date(bookingDate.getTime())); // ✅ correct
			rs = ps.executeQuery();
			while (rs.next()) {
				Booking b = new Booking();
				b.setBookingID(rs.getInt("BOOKING_ID"));
				b.setMemberID(rs.getString("MEMBER_ID"));
				b.setDeskCode(rs.getString("DESK_CODE"));
				b.setBookingDate(rs.getDate("BOOKING_DATE"));
				b.setStartTime(rs.getString("START_TIME"));
				b.setEndTime(rs.getString("END_TIME"));
				b.setStatus(rs.getString("STATUS"));
				bookingList.add(b);
			}
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
		return bookingList;
	}
}
