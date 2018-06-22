package test.bean;

public class ExcelTest {
	
	private String name;
	private Integer id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ExcelTest(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
	}
	public ExcelTest() {
		super();
	}
	
	

}
