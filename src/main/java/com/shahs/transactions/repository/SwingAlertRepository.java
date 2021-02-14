package com.shahs.transactions.repository;

        import com.shahs.transactions.model.PNLTickerSummary;
        import com.shahs.transactions.model.Price;
        import com.shahs.transactions.model.SwingAlert;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface SwingAlertRepository extends CrudRepository<SwingAlert, String> {

        @Query(value = "SELECT p.* FROM swing_alert_view p;", nativeQuery = true)
        List<SwingAlert> getAllSwingAlerts();

}

