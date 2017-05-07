package application;

public class VisitorContext {
	private final static VisitorContext instance = new VisitorContext();
	
	public static VisitorContext getInstance() {
		return instance;
	}
	
	private Visitor visitor = new Visitor();
	
	
	public Visitor currentVisitor() {
		return visitor;
	}
}
