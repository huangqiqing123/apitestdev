package test.study.easymock;

public class Business {

	private IService1 service1;
	private IService2 service2;

	//首先调用.method1()，然后调用method2()
	public void executeService1() {
		service1.method1();
		service1.method2();
	}

	//method1-->method2-->method3-->method4
	public void executeService1And2() {
		service1.method1();
		service1.method2();
		service2.method3();
		service2.method4();
	}

	public void setService1(IService1 service1) {
		this.service1 = service1;
	}

	public void setService2(IService2 service2) {
		this.service2 = service2;
	}

}
