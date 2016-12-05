import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

import com.udacity.stockhawk.data.StockHistory;

/**
 * Created by ahmed on 12/5/2016.
 */

public class StockHistoryTest {

    @Test
    public void testParseHistory1() {
        String str =
                "1479704400000, 761.679993\n" +
                        "1479099600000, 760.539978";
        List<StockHistory.Entry> historyList = StockHistory.parseHistory(str);
        Assert.assertEquals(2, historyList.size());
        assertSameEntry(1479704400000l, 761.679993, historyList.get(0));
        assertSameEntry(1479099600000l, 760.539978, historyList.get(1));
    }

    @Test
    public void testParseHistory2() {
        String str =
                "1479704400000, 761.679993";
        List<StockHistory.Entry> historyList = StockHistory.parseHistory(str);
        Assert.assertEquals(1, historyList.size());
        assertSameEntry(1479704400000l, 761.679993, historyList.get(0));
    }

    private void assertSameEntry(long time, double value, StockHistory.Entry entry) {
        Assert.assertEquals(time, entry.getTime());
        Assert.assertEquals(value, entry.getValue(), 0.00001);

    }
}
