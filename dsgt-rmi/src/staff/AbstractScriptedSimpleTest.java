package staff;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import hotel.BookingDetail;
import hotel.Room;

public abstract class AbstractScriptedSimpleTest {

	private final LocalDate today = LocalDate.now();

	/**
	 * Return true if there is no booking for the given room on the date,
	 * otherwise false
	 */
	protected abstract boolean isRoomAvailable(Integer room, LocalDate date) ;

	/**
	 * Add a booking for the given guest in the given room on the given
	 * date. If the room is not available, throw a suitable Exception.
	 */
	protected abstract void addBooking(BookingDetail bookingDetail) throws Exception;

	/**
	 * Return a list of all the available room numbers for the given date
	 */
	protected abstract Set<Integer> getAvailableRooms(LocalDate date) ;

	/**
	 * Return a list of all the room numbers
	 */
	protected abstract Set<Integer> getAllRooms() ;

	public void run() throws Exception {

		//Print all rooms of the hotel
		printAllRooms();

		isRoomAvailable(101, today); //true
		BookingDetail bd1 = new BookingDetail("Ansar", 101, today);
		addBooking(bd1);//booking success

		//Check available rooms after the first booking
		System.out.println("Printing the list of available rooms after the first booking\n");
		Integer[] expectedRoomsAfterFirstBooking = {102, 201, 203};
		checkAvailableRoomsOutput(3, expectedRoomsAfterFirstBooking);

		isRoomAvailable(102, today); //true
		BookingDetail bd2 = new BookingDetail("Smith", 102, today);
		addBooking(bd2);//booking success

		//Check available rooms after the second booking
		System.out.println("Printing the list of available rooms after the second booking\n");
		Integer[] expectedRoomsAfterSecondBooking = {201, 203};
		checkAvailableRoomsOutput(2, expectedRoomsAfterSecondBooking);

		isRoomAvailable(203, today); //false
		BookingDetail bd3 = new BookingDetail("Dimitri", 203, today);
		addBooking(bd3);//booking failure

		//Check available rooms after the booking failure
		System.out.println("Printing the list of available rooms after the third booking failure\n");
		Integer[] expectedRoomsAfterBookingFailure = {201, 203};
		checkAvailableRoomsOutput(2, expectedRoomsAfterBookingFailure);

		System.out.println("Testing concurrent bookings\n");
		Integer[] expectedAvailableRooms = {201};
		testConcurrency();
		checkAvailableRoomsOutput(1, expectedAvailableRooms);

	}

	private void checkAvailableRoomsOutput(int expectedSize, Integer[] expectedAvailableRooms) throws Exception {
		Set<Integer> availableRooms = getAvailableRooms(today);
		if (availableRooms != null && availableRooms.size() == expectedSize) {
			if (availableRooms.containsAll(Arrays.asList(expectedAvailableRooms))) {
				System.out.println("List of available rooms (room ID) " + getAvailableRooms(today) + " [CORRECT]\n");
			} else {
				System.out.println("List of available rooms (room ID) " + getAvailableRooms(today) + " [INCORRECT]\n");
			}
		} else {
			System.out.println("List of available rooms (room ID) " + getAvailableRooms(today) + " [INCORRECT]\n");
		}
	}

	private void printAllRooms() throws Exception {
		System.out.println("List of rooms (room ID) in the hotel " + getAllRooms() + "\n");
	}

    // Many clients request the same or different rooms on the same date
	// use Thread.sleep()

    private void testConcurrency() throws Exception{
		// Create a new instance of the BookingClient
		BookingClient testInstance = new BookingClient();
		// Define the date for booking attempts
		LocalDate today = LocalDate.now();

		// Define booking details for concurrent attempts
		BookingDetail bd1 = new BookingDetail("John Doe", 201, today);
		BookingDetail bd2 = new BookingDetail("Jane Doe", 201, today);
		BookingDetail bd3 = new BookingDetail("Alex Smith", 201, today); // Intentionally the same room as bd1

		// Create threads for each booking attempt
		Thread t1 = new Thread(() -> {
			try {
				Thread.sleep(100); // Simulate slight delay
				testInstance.addBooking(bd1);
				System.out.println("Booking by " + bd1.getGuest() + " for room " + bd1.getRoomNumber() + " successful.");
			} catch (Exception e) {
				System.out.println("Booking by " + bd1.getGuest() + " failed: " + e.getMessage());
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				testInstance.addBooking(bd2);
				System.out.println("Booking by " + bd2.getGuest() + " for room " + bd2.getRoomNumber() + " successful.");
			} catch (Exception e) {
				System.out.println("Booking by " + bd2.getGuest() + " failed: " + e.getMessage());
			}
		});

		Thread t3 = new Thread(() -> {
			try {
//				Thread.sleep(100); // Simulate slight delay
				testInstance.addBooking(bd3);
				System.out.println("Booking by " + bd3.getGuest() + " for room " + bd3.getRoomNumber() + " successful.");
			} catch (Exception e) {
				System.out.println("Booking by " + bd3.getGuest() + " failed: " + e.getMessage());
			}
		});

		// Start threads
		t1.start();
		t2.start();
		t3.start();

		// Wait for all threads to complete
		t1.join();
		t2.join();
		t3.join();

		// Final check of available rooms
		System.out.println("Final available rooms: " + testInstance.getAvailableRooms(today));
	}

}