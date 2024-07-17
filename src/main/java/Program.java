import dto.DepartmentDto;
import entity.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import util.HibernateUtil;


public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
                var sql = "INSERT INTO Department(id, name, type, created_at, updated_at) VALUE ('VN000001',:name, :type, NOW(), NOW())";
                var result = session.createNativeMutationQuery(sql)
                        .setParameter("name", "Kỹ thuật")
                        .setParameter("type",'D')
                        .executeUpdate();
                System.out.println("Thêm thành công = " + result);
            });
            factory.inSession(session -> {
                var sql = "SELECT * FROM Department";
                var departments = session.createNativeQuery(sql, Department.class)
                        .getResultList();
                for (Department department : departments) {
                    System.out.println("department Id = " + department.getId());
                    System.out.println("department Name = " + department.getName());
                }
            });
            factory.inSession(session -> {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<Department> query = builder.createQuery(Department.class);
                Root<Department> root = query.from(Department.class);
                Expression<Boolean> expression = builder.equal(root.get("name"), "Kỹ thuật");
                query.select(root).where(expression);
                var departments = session.createSelectionQuery(query)
                        .getResultList();
                for (Department department : departments) {
                    System.out.println("department.getId() = " + department.getId());
                    System.out.println("department.getName() = " + department.getName());
                }
            });
        }
    }
}
