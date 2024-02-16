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
	protected abstract void addBooking(BookingDetail bookingDetail);

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

		isRoomAvailable(102, today); //false
		BookingDetail bd3 = new BookingDetail("Dimitri", 102, today);
		addBooking(bd3);//booking failure

		//Check available rooms after the booking failure
		System.out.println("Printing the list of available rooms after the third booking failure\n");
		Integer[] expectedRoomsAfterBookingFailure = {201, 203};
		checkAvailableRoomsOutput(2, expectedRoomsAfterBookingFailure);

		System.out.println("Testing concurrent bookings\n");
		Integer[] expectedAvailableRooms = {201};
		testConcurrentBookings();
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
	private void testConcurrentBookings() throws Exception {
		Thread[] bookingThreads = new Thread[3]; // Assuming 3 concurrent booking attempts for simplicity

		// Simulate concurrent bookings by creating a thread for each booking attempt
		bookingThreads[0] = new Thread(() -> {
			BookingDetail bd1 = new BookingDetail("John Doe", 203, today);
			try {
				addBooking(bd1); // Attempt to book room 101
				System.out.println("Booking by " + bd1.getGuest() + " attempted.");
			} catch (Exception e) {
				System.out.println("Booking by " + bd1.getGuest() + " Smith failed: " + e.getMessage());
			}
		});

		bookingThreads[1] = new Thread(() -> {
			BookingDetail bd2 = new BookingDetail("Jane Doe", 203, today);
			try {
				addBooking(bd2); // Attempt to book room 102
				System.out.println("Booking by " + bd2.getGuest() + " attempted.");
			} catch (Exception e) {
				System.out.println("Booking by " + bd2.getGuest() + " Smith failed: " + e.getMessage());
			}
		});

		bookingThreads[2] = new Thread(() -> {
			BookingDetail bd3 = new BookingDetail("Alex Smith", 203, today);
			try {
				// Introduce a small delay to simulate real-world concurrency better
				Thread.sleep(100);
				addBooking(bd3); // Attempt to book room 101 again
				System.out.println("Booking by " + bd3.getGuest() + " attempted.");
			} catch (Exception e) {
				System.out.println("Booking by " + bd3.getGuest() + " Smith failed: " + e.getMessage());
			}
		});

		// Start all booking threads
		for (Thread t : bookingThreads) {
			t.start();
		}

		// Wait for all threads to finish
		for (Thread t : bookingThreads) {
			t.join(); // Wait for this thread to die
		}

		// Check the available rooms after the concurrent bookings
		System.out.println("Printing the list of available rooms after concurrent bookings\n");
		Set<Integer> availableRooms = getAvailableRooms(today);
		// Who booked the room 203?

		System.out.println("List of available rooms (room ID): " + availableRooms);
	}

}