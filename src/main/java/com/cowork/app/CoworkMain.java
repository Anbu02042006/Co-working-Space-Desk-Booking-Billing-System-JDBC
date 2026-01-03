package com.cowork.app;

import java.util.Calendar;
import java.util.Date;

import com.cowork.service.BookingService;
import com.cowork.util.SlotAlreadyBookedException;
import com.cowork.util.ValidationException;

public class CoworkMain {

	private static BookingService bookingService;

	public static void main(String[] args) {

		bookingService = new BookingService();

		System.out.println("--- Co-working Space Booking Console ---");

		// ---------- TEST 1: Create Booking (SUCCESS) ----------
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1); // tomorrow
			Date tomorrow = cal.getTime();

			boolean r = bookingService.createBooking("MEM9001", "D-99", tomorrow, "15:00", "18:00");

			System.out.println(r ? "BOOKING SUCCESS" : "BOOKING FAILED");

		} catch (SlotAlreadyBookedException e) {
			System.out.println("Slot Error: " + e);
		} catch (ValidationException e) {
			System.out.println("Validation Error: " + e);
		}

		// ---------- TEST 2: Cancel Booking (SUCCESS) ----------
		try {
			boolean r = bookingService.cancelBooking(900101);
			System.out.println(r ? "CANCEL SUCCESS" : "CANCEL FAILED");
		} catch (ValidationException e) {
			System.out.println("Validation Error: " + e);
		}

		// ---------- TEST 3: Generate Bill (SUCCESS) ----------
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -5);
			Date from = cal.getTime();
			Date to = new Date();

			boolean r = bookingService.generateUsageBill("MEM9001", from, to);
			System.out.println(r ? "BILL GENERATED" : "BILL GENERATION FAILED");

		} catch (ValidationException e) {
			System.out.println("Validation Error: " + e);
		}
	}
}
