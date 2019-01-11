/**
 * @program: Spring-Boot-Multi
 * @description:
 * @author: Brucezheng
 * @create: 2018-06-20 15:11
 **/
public class ConcreteSellFisher implements SellFisher {
    @Override
    public int sellFish() {
        System.out.println("my fish is delicious!!");
        return 5;
    }
}
