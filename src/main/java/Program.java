import entity.Department;
import entity.GroupAccount;
import util.HibernateUtil;

public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
               var groupAccount = new GroupAccount();
               groupAccount.setGroupId(4);
               groupAccount.setAccountId(1);
               session.persist(groupAccount);
            });
            factory.inTransaction(session -> {
                var groupAccount = new GroupAccount();
                groupAccount.setGroupId(7);
                groupAccount.setAccountId(9);
                session.persist(groupAccount);
            });

            factory.inSession(session -> {
                var hql = "FROM GroupAccount";
                var GroupAccounts = session
                        .createSelectionQuery(hql, GroupAccount.class)
                        .getResultList();
                for (var groupAccount : GroupAccounts) {
                    System.out.println("👌Group Account = " + groupAccount);
                }
            });
        }
    }
}
