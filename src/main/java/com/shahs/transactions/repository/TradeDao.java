package com.shahs.transactions.repository;

import com.shahs.transactions.model.Price;
import com.shahs.transactions.util.MiscUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TradeDao {

    @Autowired
    JdbcTemplate tradeJdbcTmpl;

    public void test() {
        System.out.println(tradeJdbcTmpl.queryForList("select count(*) from trade"));
    }

    public void addUpdatePrice(Price p) {
        String dt = MiscUtils.dateToString(p.getPriceDate(),"yyyy-MM-dd");
        String query = "insert into price (price_date, ticker, close, open, volume) values (" +
                "'" + dt + "','" + p.getTicker() + "'," + p.getClose() + "," + p.getOpen() + "," + p.getVolume() +
                ") ON DUPLICATE KEY UPDATE close = " + p.getClose() + ", open = " + p.getOpen() + " , volume=" + p.getVolume();

        tradeJdbcTmpl.update(query);

    }

}
