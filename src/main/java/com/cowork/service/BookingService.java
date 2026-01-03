package com.cowork.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.cowork.bean.Bill;
import com.cowork.bean.Booking;
import com.cowork.bean.Member;
import com.cowork.dao.BillDAO;
import com.cowork.dao.BookingDAO;
import com.cowork.dao.MemberDAO;
import com.cowork.util.ActiveBillingException;
import com.cowork.util.DBUtil;
import com.cowork.util.SlotAlreadyBookedException;
import com.cowork.util.ValidationException;

public class BookingService {

	private MemberDAO memberDAO = new MemberDAO();
	private BillDAO billDAO = new BillDAO();
	private BookingDAO bookingDAO = new BookingDAO();

	public Member viewMemberDetails(String memberID) throws ValidationException {
		if (memberID == null || memberID.trim().isEmpty()) {
			throw new ValidationException("Member ID must not be blank");
		}
		return memberDAO.findMember(memberID.trim());
	}

	public List<Member> viewAllMembers() {
		return memberDAO.viewAllMembers();
	}

	public boolean addNewMember(Member member) throws ValidationException {
		if (member == null)
			throw new ValidationException("Member details must not be null");

		if (member.getMemberID() == null || member.getMemberID().trim().isEmpty())
			throw new ValidationException("Member ID must not be blank");

		if (member.getFullName() == null || member.getFullName().trim().isEmpty())
			throw new ValidationException("Full name must not be empty");

		if (member.getEmail() == null || member.getEmail().trim().isEmpty())
			throw new ValidationException("Email must not be empty");

		if (!member.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
			throw new ValidationException("Invalid email format");

		if (member.getMobile() == null || member.getMobile().trim().isEmpty())
			throw new ValidationException("Mobile number must not be empty");

		if (memberDAO.findMember(member.getMemberID()) != null)
			throw new ValidationException("Member ID already exists");

		member.setOutstandingBalance(0.0);
		return memberDAO.insertMember(member);
	}

	public boolean removeMember(String memberID) throws ValidationException, ActiveBillingException {

		if (memberID == null || memberID.trim().isEmpty())
			throw new ValidationException("Member ID must not be blank");

		memberID = memberID.trim();

		if (!billDAO.findPendingBillsByMember(memberID).isEmpty())
			throw new ActiveBillingException();

		List<Booking> bookings = bookingDAO.findBookingsByMember(memberID);
		for (Booking b : bookings) {
			if ("BOOKED".equalsIgnoreCase(b.getStatus())) {
				throw new ValidationException("Member has active bookings. Cannot remove member.");
			}
		}
		return memberDAO.deleteMember(memberID);
	}

	public boolean createBooking(String memberID, String deskCode, Date bookingDate, String startTime, String endTime)
			throws ValidationException, SlotAlreadyBookedException {

		Connection conn = null;

		try {
			if (memberID == null || memberID.trim().isEmpty())
				throw new ValidationException("Member ID must not be blank");

			if (deskCode == null || deskCode.trim().isEmpty())
				throw new ValidationException("Desk code must not be blank");

			if (bookingDate == null)
				throw new ValidationException("Booking date is required");

			if (startTime.compareTo(endTime) >= 0)
				throw new ValidationException("Start time must be earlier than end time");

			Member member = memberDAO.findMember(memberID.trim());
			if (member == null)
				return false;

			List<Booking> existing = bookingDAO.findBookingsForDeskAndDate(deskCode, bookingDate);

			for (Booking b : existing) {
				if ("BOOKED".equalsIgnoreCase(b.getStatus()) || "COMPLETED".equalsIgnoreCase(b.getStatus())) {

					if (startTime.compareTo(b.getEndTime()) < 0 && endTime.compareTo(b.getStartTime()) > 0) {
						throw new SlotAlreadyBookedException();
					}
				}
			}

			conn = DBUtil.getDBConnection();
			conn.setAutoCommit(false);

			Booking booking = new Booking();
			booking.setBookingID(bookingDAO.generateBookingID());
			booking.setMemberID(memberID.trim());
			booking.setDeskCode(deskCode.trim());
			booking.setBookingDate(bookingDate);
			booking.setStartTime(startTime);
			booking.setEndTime(endTime);
			booking.setStatus("BOOKED");

			if (bookingDAO.recordBooking(booking)) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}

		} catch (SlotAlreadyBookedException | ValidationException e) {
			throw e;

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;

		} finally {
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean cancelBooking(int bookingID) throws ValidationException {

		if (bookingID <= 0)
			throw new ValidationException("Booking ID must be positive");

		Connection conn = null;
		try {
			List<Booking> bookings = bookingDAO.findBookingsByMember(null);
			Booking target = null;

			for (Booking b : bookings) {
				if (b.getBookingID() == bookingID) {
					target = b;
					break;
				}
			}

			if (target == null || "CANCELLED".equalsIgnoreCase(target.getStatus())
					|| "COMPLETED".equalsIgnoreCase(target.getStatus())) {
				return false;
			}

			conn = DBUtil.getDBConnection();
			conn.setAutoCommit(false);

			if (bookingDAO.updateBookingStatus(bookingID, "CANCELLED")) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return false;
		} finally {
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean generateUsageBill(String memberID, Date from, Date to) throws ValidationException {

		if (memberID == null || memberID.trim().isEmpty())
			throw new ValidationException("Member ID must not be blank");

		if (from.after(to))
			throw new ValidationException("Invalid billing period");

		Connection conn = null;

		try {
			Member member = memberDAO.findMember(memberID.trim());
			if (member == null)
				return false;

			double totalHours = 0;
			List<Booking> bookings = bookingDAO.findBookingsByMember(memberID.trim());

			for (Booking b : bookings) {
				if ("COMPLETED".equalsIgnoreCase(b.getStatus()) && !b.getBookingDate().before(from)
						&& !b.getBookingDate().after(to)) {

					String[] s = b.getStartTime().split(":");
					String[] e = b.getEndTime().split(":");

					int start = Integer.parseInt(s[0]) * 60 + Integer.parseInt(s[1]);
					int end = Integer.parseInt(e[0]) * 60 + Integer.parseInt(e[1]);

					totalHours += (end - start) / 60.0;
				}
			}

			if (totalHours <= 0)
				return false;

			double rate = "BASIC".equalsIgnoreCase(member.getMembershipTier()) ? 200
					: "STANDARD".equalsIgnoreCase(member.getMembershipTier()) ? 150 : 100;

			double amount = totalHours * rate;

			conn = DBUtil.getDBConnection();
			conn.setAutoCommit(false);

			Bill bill = new Bill();
			bill.setBillID(billDAO.generateBillID());
			bill.setMemberID(memberID.trim());
			bill.setBillingPeriodFrom(from);
			bill.setBillingPeriodTo(to);
			bill.setTotalHours(totalHours);
			bill.setAmount(amount);
			bill.setStatus("PENDING");

			if (billDAO.recordBill(bill)
					&& memberDAO.updateOutstandingBalance(memberID.trim(), member.getOutstandingBalance() + amount)) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return false;
		} finally {
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
