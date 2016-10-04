

public class Skrip {

	private String HTML = "<!DOCTYPE html>\n<html>\n<head>\n<title></title>\n" +
			               "<link href=\"\" rel=\"stylesheet\"/>\n<script src=\"\"></script>\n"+
			               "</head\n<body>\n<h1>Hello World</h1>\n</body>\n</html>";
	private String JS   = "<!DOCTYPE html>\n<html>\n<head>\n<title></title>\n<script type=\"text/javascript\">\n\n\n</script>\n</head\n<body>\n<h1>Hello World</h1>\n</body>\n</html>";
	private String CSS   = "<!DOCTYPE html>\n<html>\n<head>\n<title></title>\n<style>\n\n\n</style>\n</head\n<body>\n<h1>Hello World</h1>\n</body>\n</html>";
	
	public String[] ext = {HTML,CSS,JS};
	
	public int CHOOSE;
	public Skrip(){
		CHOOSE = 0;
	}
	public String getLang(int i){ //output String
		String value;
		value = ext[i];
		return value;
	}

	public int setPilihan(int apa) {
		// TODO Auto-generated method stub
		return this.CHOOSE+apa;
	}
	
	public int getChoose(){
		return CHOOSE;
	}

}
