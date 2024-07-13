import entity.Department;
import util.HibernateUtil;

public class Program {
    public static void main(String[] args) {
        try(var factory = HibernateUtil.buildSessionFactory()){
            factory.inTransaction(session -> {
                var department = new Department();
                department.setName("Giám Đốc");
                department.setType(Department.Type.PROJECT_MANERGER);
                session.persist(department);
            });
            factory.inTransaction(session -> {
                var department = new Department();
                department.setName("Bảo Vệ");
                department.setType(Department.Type.TESTER);
                session.persist(department);
            });

            factory.inSession(session -> {
                var hpl = "FROM Department";
                var departments = session
                        .createSelectionQuery(hpl, Department.class)
                        .getResultList();
                for (var department : departments) {
                    System.out.println("🎈department = " + department);
                }
            });


        }
    }
}
