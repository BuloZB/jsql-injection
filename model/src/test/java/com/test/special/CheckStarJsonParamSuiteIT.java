package com.test.special;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.subscriber.SubscriberLogger;
import com.test.engine.mysql.ConcreteMysqlSuiteIT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junitpioneer.jupiter.RetryingTest;

class CheckStarJsonParamSuiteIT extends ConcreteMysqlSuiteIT {
    
    @Override
    public void setupInjection() throws Exception {
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.subscribe(new SubscriberLogger(model));

        model.getMediatorUtils().parameterUtil().initQueryString("http://localhost:8080/json");
        model.getMediatorUtils().parameterUtil().initRequest("""
            tenant=mysql&name={
                "c": 1,
                "b": {
                    "b": [
                        1,
                        true,
                        null,
                        {
                            "a": {
                                "a": "*"
                            }
                        }
                    ]
                }
            }
        """);

        model.setIsScanning(true);
        
        model
        .getMediatorUtils()
        .preferencesUtil()
        .withIsCheckingAllRequestParam(false)
        .withIsCheckingAllJsonParam(false)
        .withIsStrategyTimeDisabled(true)
        .withIsStrategyBlindBinDisabled(true)
        .withIsStrategyBlindBitDisabled(true)
        .withIsStrategyMultibitDisabled(true);

        model
        .getMediatorUtils()
        .connectionUtil()
        .withMethodInjection(model.getMediatorMethod().getRequest())
        .withTypeRequest("POST");
        
        model.beginInjection();
    }
    
    @Override
    @RetryingTest(3)
    public void listDatabases() throws JSqlException {
        super.listDatabases();
    }

    @AfterEach
    void afterEach() {
        Assertions.assertEquals(
            this.injectionModel.getMediatorStrategy().getUnion(),
            this.injectionModel.getMediatorStrategy().getStrategy()
        );
    }
}
