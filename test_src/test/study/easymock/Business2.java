package test.study.easymock;

import java.util.Random;

public class Business2 {
	
	private IService3 service;
	
	public void execute() {	
		Random random = new Random();
		int count = random.nextInt() / 10000;;
		int result = service.execute(count);
		System.out.println("result="+result);
	}

	public void setService(IService3 service) {
		this.service = service;
	}
}
