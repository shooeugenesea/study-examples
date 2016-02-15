package examples.mbean;

public class TestMXBeanImpl implements TestMXBean {

    private volatile String name;
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(name);
    }
    
}
