package com.shahs.transactions.model;
import java.io.Serializable;

public class PNLDataKey implements Serializable {
    private long sellTradeId;
    private long buyTradeId;

    @Override
    public int hashCode() {
        return Integer.parseInt(this.sellTradeId + this.buyTradeId + " ");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of PNLDataKey or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof PNLDataKey)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        PNLDataKey pdk = (PNLDataKey) obj;

        // Compare the data members and return accordingly
        return sellTradeId == pdk.sellTradeId &&
                buyTradeId == pdk.buyTradeId;
    }
}
