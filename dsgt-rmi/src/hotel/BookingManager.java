package hotel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class BookingManager {

	private Room[] rooms;
	private final Map<Integer, Room> roomMap;

	// main
	public static void main(String[] args) {
		BookingManager bm = new BookingManager();
		System.out.println(bm.isRoomAvailable(101, LocalDate.now())); // true
		BookingDetail bd1 = new BookingDetail("Ansar", 101, LocalDate.now());
		bm.addBooking(bd1);
		System.out.println(bm.isRoomAvailable(101, LocalDate.now())); // false
		System.out.println(bm.getAvailableRooms(LocalDate.now())); // [102, 201, 203]
	}


	public BookingManager() {
		Room[] rooms = initializeRooms();
		roomMap = new ConcurrentHashMap<>(); // Use ConcurrentHashMap for thread-safe access
		for (Room room : rooms) {
			roomMap.put(room.getRoomNumber(), room);
		}
	}

	public Set<Integer> getAllRooms() {
		return new HashSet<>(roomMap.keySet());
	}

	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		Room room = roomMap.get(roomNumber);
		if (room != null) {
			synchronized (room) { // Synchronize on the room object
				for (BookingDetail booking : room.getBookings()) {
					if (booking.getDate().equals(date)) { // Use equals for LocalDate comparison
						return false;
					}
				}
			}
		}
		return true;
	}

	public void addBooking(BookingDetail bookingDetail) {
		Room room = roomMap.get(bookingDetail.getRoomNumber());
		if (room != null) {
			synchronized (room) { // Synchronize on the room object
				room.getBookings().add(bookingDetail);
			}
		}
	}

	public Set<Integer> getAvailableRooms(LocalDate date) {
		Set<Integer> availableRooms = new HashSet<>();
		for (Room room : roomMap.values()) {
			if (isRoomAvailable(room.getRoomNumber(), date)) {
				availableRooms.add(room.getRoomNumber());
			}
		}
		return availableRooms;
	}

	private static Room[] initializeRooms() {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);
		return rooms;
	}
}
