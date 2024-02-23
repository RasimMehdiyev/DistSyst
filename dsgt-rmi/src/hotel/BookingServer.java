package hotel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BookingServer {
    private Room[] rooms;

    public BookingServer() {
        this.rooms = initializeRooms();
    }

    public Room[] initializeRooms() {
        Room[] rooms = new Room[3];
        rooms[0] = new Room(101);
        rooms[1] = new Room(102);
        rooms[2] = new Room(103);
        return rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
        // implement this method
        if (rooms != null) {
            for (Room room : rooms) {
                if (Objects.equals(room.getRoomNumber(), roomNumber)) {
                    for (BookingDetail booking : room.getBookings()) {
                        if (booking.getDate() == date) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public void addBooking(BookingDetail bookingDetail) {
        // implement this method
        if (rooms != null) {
            for (Room room : rooms) {
                if (Objects.equals(room.getRoomNumber(), bookingDetail.getRoomNumber())) {
                    room.getBookings().add(bookingDetail);
                }

            }
        }
    }

    public Set<Integer> getAvailableRooms(LocalDate date) {
        // implement this method
        Set<Integer> availableRooms = new HashSet<Integer>();
        if (rooms != null) {
            for (Room room : rooms) {
                if (isRoomAvailable(room.getRoomNumber(), date)) {
                    availableRooms.add(room.getRoomNumber());
                }
            }
        }
        return availableRooms;
    }

    public Set<Integer> getAllRooms() {
        Set<Integer> allRooms = new HashSet<Integer>();
        Iterable<Room> roomIterator = Arrays.asList(rooms);
        for (Room room : roomIterator) {
            allRooms.add(room.getRoomNumber());
        }
        return allRooms;
    }
}
