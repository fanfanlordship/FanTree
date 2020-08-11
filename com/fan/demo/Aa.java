package fan.demo;

/**
 * @description:
 * @author: fanfanlordship
 * @create: 2020-08-11 23:33
 */
public class Aa {

    private Integer id;

    public Aa(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Aa{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
