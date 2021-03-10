package application.model;

public class TestEnumMonth {

	public static void main(String[] args) {
		System.out.println(EnumMonth.JANUARY.get());
		System.out.println(EnumMonth.valueOf("JANUARY"));
		System.out.println(EnumMonth.values());
		for(EnumMonth m : EnumMonth.values()) {
			System.out.println(m.get());
		}
		EnumMonth m;
		System.out.println(EnumMonth.getEnumByString("Januar"));
	}
}
