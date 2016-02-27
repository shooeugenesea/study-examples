package dsaa.recursion;

public class DrawTicks {

	public String drawTicks(int inch, int tickLength) {
		StringBuilder s = new StringBuilder();
		s.append(drawTick(tickLength));
		for ( int i = 0; i < inch; i++ ) {
			s.append(drawTicks(tickLength-1));
			s.append(drawTick(tickLength));
		}
		return s.toString();
	}

	private String drawTicks(int tickLength) {
		if ( tickLength == 1 ) {
			return drawTick(1);
		}
		StringBuilder s = new StringBuilder();
		s.append(drawTicks(tickLength-1));
		s.append(drawTick(tickLength));
		s.append(drawTicks(tickLength-1));
		return s.toString();
	}
	
	private String drawTick(int tickLength) {
		StringBuilder s = new StringBuilder();
		for ( int i = 0; i < tickLength; i++ ) {
			s.append("-");
		}
		s.append("\n");
		return s.toString();
	}
	
}
