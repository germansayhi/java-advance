import dto.DepartmentDto;
import entity.*;
import util.HibernateUtil;


public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
                var department1 = new Department();
                department1.setName("Giám Đốc");
                department1.setType(Department.Type.PROJECT_MANAGER);
                session.persist(department1);
            });

                factory.inTransaction(session -> {
                var department2 = new Department();
                department2.setName("Bảo Vệ");
                department2.setType(Department.Type.TESTER);
                session.persist(department2);
            });
                factory.inSession(session -> {
                    var hpl = "FROM Department";
                    var departments = session
                            .createSelectionQuery(hpl, Department.class)
                            .getResultList();
                    for (var department :departments){
                        System.out.println("🎈department= " + department.getName());
                        System.out.println("🎈department= " + department.getId());
                    }
                });
                factory.inSession(session -> {
                    // var hpl = "FROM Department WHERE id =?1";
                    var hpl = "FROM Department WHERE id = :id";
                    var department = session
                            .createSelectionQuery(hpl, Department.class)
                         //   .setParameter(1, "VN000001");
                            .setParameter("id","VN000002")
                            .uniqueResult();
                    System.out.println("❌department Id = " + department.getId());
                    System.out.println("❌department Name = " + department.getName());

                });
                factory.inSession(session -> {
                    var hpl =" SELECT COUNT(*) FROM Department ";
                    var count = session.createSelectionQuery(hpl, Long.class)
                            .uniqueResult();
                    System.out.println("😒count = " + count);
                });

                factory.inSession(session -> {
                    var hpl ="SELECT new DepartmentDto(name) FROM  Department";
                    var departments = session.createSelectionQuery(hpl, DepartmentDto.class)
                            .getResultList();
                    for (var department : departments) {
                        System.out.println("❤️department Name = " + department.getName());
                    }
                });

            factory.inSession(session -> {
                var page = 2;
                var size = 1;
                var hpl = "FROM Department";
                var departments = session
                        .createSelectionQuery(hpl, Department.class)
                        .setMaxResults(size)
                        .setFirstResult((page -1) *size)
                        .getResultList();
                for (var department :departments){
                    System.out.println("🎈department= " + department.getName());
                    System.out.println("🎈department= " + department.getId());
                }
            });
            factory.inTransaction(session -> {
                var hpl = "DELETE FROM Department WHERE id =:id";
               var result= session.createMutationQuery(hpl)
                        .setParameter("id", "VN000001")
                        .executeUpdate();
                System.out.println("Xóa thành công = " + result);
            });
        }
    }
}
