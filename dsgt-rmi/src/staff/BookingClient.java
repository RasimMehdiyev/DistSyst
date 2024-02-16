package staff;

import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

	private BookingManager bm = null;

	public static void main(String[] args) throws Exception {
		BookingClient client = new BookingClient();
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient() {
		try {
			//Look up the registered remote instance
			bm = new BookingManager();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		//Implement this method
        return bm.isRoomAvailable(roomNumber, date);
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) {
		//Implement this method
	    if (isRoomAvailable(bookingDetail.getRoomNumber(), bookingDetail.getDate())) {
	        bm.addBooking(bookingDetail);
	    } else {
//	        throw new Exception("Room not available");
			System.out.println("Room not available");
	    }

	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		//Implement this method
		if (bm.getAvailableRooms(date) != null) {
			return bm.getAvailableRooms(date);
		}
		return null;
	}

	@Override
	public Set<Integer> getAllRooms() {
		return bm.getAllRooms();
	}
}
