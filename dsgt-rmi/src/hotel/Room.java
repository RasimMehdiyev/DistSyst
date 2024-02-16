package hotel;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private Integer roomNumber;
	private List<BookingDetail> bookings;

	public Room(Integer roomNumber) {
		this.roomNumber = roomNumber;
		bookings = new ArrayList<BookingDetail>();
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public List<BookingDetail> getBookings() {
		return bookings;
	}

	public void setBookings(List<BookingDetail> bookings) {
		this.bookings = bookings;
	}
	public void getRoomDetails(Integer roomNumber) {
		System.out.println("Room Number: " + roomNumber);
		System.out.println("Bookings: ");
		for (BookingDetail booking : bookings) {
			System.out.println(booking.getGuest() + " " + booking.getDate());
		}
	}
}