import com.ocado.basket.BasketSplitter;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

import static com.ocado.basket.Main.getBasket;

public class BasketSplitterTest {
    BasketSplitter bs = new BasketSplitter("config.json");
    @Test
    public void testEmptyBasket() {
        List<String> basket = getBasket("basket-empty.json");
        Map<String, List<String>> solution = bs.split(basket);
        assertTrue(solution.isEmpty());
    }

    @Test
    public void testResultSizes() {
        // tests, if all products in basket are assigned a delivery option

        List<String> basket1 = getBasket("basket-1.json");
        Map<String, List<String>> solution1 = bs.split(basket1);
        int size = 0;
        for (List<String> list : solution1.values()) {
            size += list.size();
        }
        assertEquals(basket1.size(), size);

        List<String> basket2 = getBasket("basket-2.json");
        Map<String, List<String>> solution2 = bs.split(basket2);
        size = 0;
        for (List<String> list : solution2.values()) {
            size += list.size();
        }
        assertEquals(basket2.size(), size);
    }
}
