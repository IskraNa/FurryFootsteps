package org.example.furryfootstepsapi.model.requests;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

public class AvailabilityRequest {
    public Long postId;
    public DateTimeLiteralExpression.DateTime dateTimeFrom;
    public DateTimeLiteralExpression.DateTime dateTimeTo;

}
