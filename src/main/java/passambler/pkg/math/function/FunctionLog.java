package passambler.pkg.math.function;

import java.math.BigDecimal;

public class FunctionLog extends FunctionSimpleMath {
    @Override
    public BigDecimal getValue(BigDecimal value) {
        return new BigDecimal(Math.log(value.doubleValue()));
    }
}
