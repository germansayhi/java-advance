import dto.DepartmentDto;
import entity.*;
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
        }
    }
}
