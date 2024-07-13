package generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class DepartmentIdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        String hlq = "SELECT COUNT(*) FROM Department";
        long count = session
                .createSelectionQuery(hlq,Long.class )
                .uniqueResult();
        return String.format("VN%06d", count+1);
    }
}
